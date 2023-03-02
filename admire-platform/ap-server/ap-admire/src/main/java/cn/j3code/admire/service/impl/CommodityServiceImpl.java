package cn.j3code.admire.service.impl;

import cn.j3code.admire.bo.SaveOrderBO;
import cn.j3code.admire.controller.api.web.query.CommodityListQuery;
import cn.j3code.admire.controller.api.web.vo.CommodityVO;
import cn.j3code.admire.controller.api.web.vo.LookContentVO;
import cn.j3code.admire.controller.api.web.vo.PayCommodityVO;
import cn.j3code.admire.converter.CommodityConverter;
import cn.j3code.admire.enums.CommodityTypeEnum;
import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.mapper.CommodityMapper;
import cn.j3code.admire.po.Commodity;
import cn.j3code.admire.po.Order;
import cn.j3code.admire.service.CommodityService;
import cn.j3code.admire.service.OrderService;
import cn.j3code.config.constant.ServerNameConstants;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.exception.ApException;
import cn.j3code.config.util.ApUtil;
import cn.j3code.config.util.AssertUtil;
import cn.j3code.config.util.SecurityUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author J3code
 * @description 针对表【ap_commodity】的数据库操作Service实现
 * @createDate 2023-02-04 12:01:32
 */
@Slf4j
@AllArgsConstructor
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity>
        implements CommodityService {

    private final OrderService orderService;
    private final CommodityConverter commodityConverter;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public IPage<CommodityVO> page(CommodityListQuery query) {
        if (query.getCurrent() > 1) {
            String authorization = SecurityUtil.get("authorization");
            if (StringUtils.isBlank(authorization) || Boolean.FALSE.equals(redisTemplate.hasKey(ApUtil.loginRedisKey(authorization)))) {
                throw new ApException(ApExceptionEnum.PROMPT_LOGIN_ACCESS);
            }
        }
        // 查询已购买商品
        if (Objects.nonNull(query.getIsPay()) && query.getIsPay().equals(1)) {
            query.setUserId(Objects.requireNonNull(SecurityUtil.getUserId(), ApExceptionEnum.DATA_ERROR.getDescription()));
            return getBaseMapper().pagePay(new Page<CommodityVO>(query.getCurrent(), query.getSize()), query);
        }
        // 查询未购买商品
        if (Objects.nonNull(query.getIsPay()) && query.getIsPay().equals(2)) {
            query.setUserId(Objects.requireNonNull(SecurityUtil.getUserId(), ApExceptionEnum.DATA_ERROR.getDescription()));
            return getBaseMapper().pageNotPay(new Page<CommodityVO>(query.getCurrent(), query.getSize()), query);
        }
        // 查询所有商品
        return getBaseMapper().page(new Page<CommodityVO>(query.getCurrent(), query.getSize()), query);
    }

    @Override
    public CommodityVO one(Long id) {
        CommodityVO vo = getBaseMapper().one(id);

        if (Objects.isNull(vo)){
            throw new ApException(ApExceptionEnum.NOT_DATA_ERROR);
        }

        // 收费内容，一律置空返回，只有付款后才可观看
        if (CommodityTypeEnum.CHARGE.getValue().equals(vo.getType())) {
            vo.setContent(null);
        }
        // redis 商品查看人数 +1
        redisTemplate.opsForValue().increment(ServerNameConstants.COMMODITY_LOOK_NUMBER_KEY + vo.getId());
        // 返回结果
        return vo;
    }

    @Override
    public LookContentVO lookContent(Long id) {
        LookContentVO lookContentVO = new LookContentVO();

        // 免费商品直接返回
        Commodity commodity = getById(id);
        // 判空
        AssertUtil.isTrue(Objects.isNull(commodity), ApExceptionEnum.NOT_DATA_ERROR.getDescription());

        // 免费商品直接返回
        if (Objects.nonNull(commodity) && commodity.getType() == 2) {
            // 免费商品，返回内容出去
            lookContentVO.setContent(commodity.getContent());
            lookContentVO.setPay(Boolean.TRUE);
            return lookContentVO;
        }

        // 判断用户是否支付该商品
        int count = getBaseMapper().getLogCountByCommodityIdAndUserId(id, Objects.requireNonNull(SecurityUtil.getUserId(), "用户id获取失败！"));
        if (count == 1) {
            // 已购买，返回内容出去
            lookContentVO.setContent(commodity.getContent());
            lookContentVO.setPay(Boolean.TRUE);
            return lookContentVO;
        }

        // 判断是否有未支付订单
        Order order = orderService.getOrderBy(
                id, Objects.requireNonNull(SecurityUtil.getUserId(), "用户id获取失败！"));
        if (Objects.isNull(order)) {
            lookContentVO.setExistOrder(Boolean.FALSE);
            lookContentVO.setPay(Boolean.FALSE);
            lookContentVO.setContent("付费内容，请付费查看！");
            return lookContentVO;
        }

        if (OrderStatusEnums.NOT_PAY.getValue().equals(order.getPayStatus())) {
            lookContentVO.setExistOrder(Boolean.TRUE);
            lookContentVO.setPay(Boolean.FALSE);
            lookContentVO.setContent("商品存在未支付订单，请支付后再查看！");
            return lookContentVO;
        }
        throw new ApException(ApExceptionEnum.DATA_ERROR);
    }

    @Override
    public PayCommodityVO payCommodity(Long id) {
        /**
         * 支付商品
         * 1、判断商品是否存在
         * 2、判断是否已经支付
         * 3、获取支付二维码
         */
        Commodity commodity = getById(id);
        if (Objects.isNull(commodity)) {
            throw new ApException(ApExceptionEnum.NOT_DATA_ERROR);
        }
        // 判断用户是否支付该商品
        int count = getBaseMapper().getLogCountByCommodityIdAndUserId(commodity.getId(), Objects.requireNonNull(SecurityUtil.getUserId(), "用户id获取失败！"));
        if (count == 1) {
            // 已购买
            throw new ApException("土豪爸爸，我是有原则的，只收一次费用！");
        }
        // 插入订单
        SaveOrderBO saveOrderBO = orderService.saveByCommodity(commodity, ServerNameConstants.ALI_CALLBACK_PAY_COMMODITY_URL);

        PayCommodityVO vo = new PayCommodityVO();
        vo.setQrCode(saveOrderBO.getQrCode());
        vo.setName(commodity.getName());
        vo.setPrice(commodity.getPrice());
        return vo;
    }

    @Override
    public void setCommodityLookNumber() {
        List<Commodity> commodityList = lambdaQuery().list();
        List<Commodity> updateCommodityList = new ArrayList<>();
        for (Commodity commodity : commodityList) {
            String key = ServerNameConstants.COMMODITY_LOOK_NUMBER_KEY + commodity.getId();
            if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
                redisTemplate.opsForValue().set(key, commodity.getLookNumber());
                continue;
            }
            Integer lookNumber = (Integer) redisTemplate.opsForValue().get(key);
            if (commodity.getLookNumber().equals(lookNumber)) {
                continue;
            }
            Commodity update = new Commodity();
            update.setId(commodity.getId());
            update.setLookNumber((Integer) redisTemplate.opsForValue().get(key));
            updateCommodityList.add(update);
        }

        if (CollectionUtils.isNotEmpty(updateCommodityList)) {
            updateBatchById(updateCommodityList);
        }
    }

    @Override
    public void setCommodityBuyNumber() {
        // 获取商品购买人数列表
        List<Commodity> commodityList = getBaseMapper().getCommodityBuyNumber();

        if (CollectionUtils.isEmpty(commodityList)) {
            return;
        }

        // 商品id、与购买人数map
        Map<Long, Integer> buyNumIdMap = commodityList.stream().collect(Collectors.toMap(Commodity::getId, Commodity::getBuyNumber));
        // 商品列表，根据已经购买的商品id
        List<Commodity> list = lambdaQuery().in(Commodity::getId, commodityList.stream().map(Commodity::getId).collect(Collectors.toSet()))
                .list();

        // 更新列表
        List<Commodity> updateList = new ArrayList<>();
        list.forEach(item -> {
            // 如果商品购买人数未改变，则不修改
            if (!item.getBuyNumber().equals(buyNumIdMap.get(item.getId()))) {
                Commodity commodity = new Commodity();
                commodity.setId(item.getId());
                commodity.setBuyNumber(buyNumIdMap.get(item.getId()));
                updateList.add(commodity);
            }
        });

        if (CollectionUtils.isEmpty(updateList)) {
            return;
        }
        // 更新
        updateBatchById(updateList);
    }

}




