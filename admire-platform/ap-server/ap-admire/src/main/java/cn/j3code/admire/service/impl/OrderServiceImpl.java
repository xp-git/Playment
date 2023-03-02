package cn.j3code.admire.service.impl;

import cn.j3code.admire.bo.SaveOrderBO;
import cn.j3code.admire.controller.api.web.query.OrderListQuery;
import cn.j3code.admire.controller.api.web.vo.OrderVO;
import cn.j3code.admire.controller.api.web.vo.PayCommodityVO;
import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.feign.UserFeign;
import cn.j3code.admire.handler.CallbackHandler;
import cn.j3code.admire.mapper.CommodityMapper;
import cn.j3code.admire.mapper.OrderMapper;
import cn.j3code.admire.pay.AliPay;
import cn.j3code.admire.po.Commodity;
import cn.j3code.admire.po.Order;
import cn.j3code.admire.po.UserCommodity;
import cn.j3code.admire.service.OrderService;
import cn.j3code.admire.service.PayLogService;
import cn.j3code.admire.service.UserCommodityService;
import cn.j3code.common.annotation.DistributedLock;
import cn.j3code.config.constant.ServerNameConstants;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.exception.ApException;
import cn.j3code.config.util.AssertUtil;
import cn.j3code.config.util.SecurityUtil;
import cn.j3code.config.util.SnowFlakeUtil;
import cn.j3code.config.vo.feign.UserVO;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author J3code
 * @description 针对表【ap_order】的数据库操作Service实现
 * @createDate 2023-02-04 12:01:46
 */
@Slf4j
@AllArgsConstructor
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    private final CommodityMapper commodityMapper;
    private final PayLogService payLogService;

    private final UserCommodityService userCommodityService;
    private final TransactionTemplate transactionTemplate;
    private final UserFeign userFeign;
    private final AliPay aliPay;
    private final List<CallbackHandler> callbackHandlerList;


    @Override
    public void updateOrderStatusAndUserId(String orderNumber, UserVO userVO, OrderStatusEnums status) {
        Order order = lambdaQuery()
                .eq(Order::getOrderNumber, orderNumber)
                .one();
        if (Objects.isNull(order)) {
            throw new ApException(ApExceptionEnum.ORDER_STATUS_UPDATE_ERROR_NOT_DATA);
        }
        order.setUserId(userVO.getId());
        order.setPayStatus(status.getValue());

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setUserId(order.getUserId());
        updateOrder.setPayStatus(order.getPayStatus());
        updateById(updateOrder);
        // 插入账户流水
        payLogService.saveBy(order, commodityMapper.selectById(order.getCommodityId()));
        saveUserCommodity(order);
    }

    @Override
    public Order getOrderBy(Long commodityId, Long userId) {

        return lambdaQuery().eq(Order::getCommodityId, commodityId)
                .eq(Order::getUserId, userId)
                .one();
    }

    @Override
    public SaveOrderBO saveByCommodity(Commodity commodity, String callbackUrl) {
        SaveOrderBO saveOrderBO = new SaveOrderBO();
        saveOrderBO.setCommodity(commodity);
        String qrCode = "";
        // 订单编号
        String orderNumber = SnowFlakeUtil.getId().toString();
        saveOrderBO.setOrderNumber(orderNumber);
        try {
            qrCode = aliPay.getCode(
                    orderNumber,
                    commodity.getName(),
                    commodity.getPrice().toString(),
                    "",
                    callbackUrl
            );
            saveOrderBO.setQrCode(qrCode);
        } catch (AlipayApiException e) {
            throw new ApException("获取支付二维码出错！");
        }

        if (StringUtils.isEmpty(saveOrderBO.getQrCode())) {
            throw new ApException("获取支付二维码出错！");
        }

        Order order = new Order();

        Long userId = SecurityUtil.getUserId();
        if ("0".equals(userId.toString())) {
            userId = Long.parseLong(saveOrderBO.getOrderNumber());
        }
        order.setUserId(userId);
        order.setCommodityId(saveOrderBO.getCommodity().getId());
        order.setOrderNumber(saveOrderBO.getOrderNumber());
        order.setCallbackUrl(callbackUrl);
        order.setMoney(saveOrderBO.getCommodity().getPrice());
        order.setPayType(1);
        order.setPayStatus(OrderStatusEnums.NOT_PAY.getValue());
        order.setExpireTime(LocalDateTime.now().plusHours(1).plusMinutes(30));
        order.setQrCode(saveOrderBO.getQrCode());
        save(order);

        return saveOrderBO;
    }

    /**
     * 这段逻辑就是将未支付订单改为已支付逻辑
     *
     * @param orderNumber
     * @param updateOrder
     * @param lodOrder
     * @return
     */
    @DistributedLock
    public Boolean runUpdate(String orderNumber, Order updateOrder, Order lodOrder) {
        Order newOrder = lambdaQuery()
                .eq(Order::getOrderNumber, orderNumber)
                .one();
        if ("1".equals(newOrder.getPayStatus().toString())) {
            return Boolean.TRUE;
        }

        return transactionTemplate.execute(trStatus -> {
            try {
                updateById(updateOrder);
                payLogService.saveBy(lodOrder, commodityMapper.selectById(lodOrder.getCommodityId()));
                saveUserCommodity(lodOrder);
            } catch (Exception e) {
                // 错误处理
                log.error("支付宝回调执行逻辑失败：", e);
                trStatus.setRollbackOnly();
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        });
    }

    @Override
    public void updateOrderStatus(String orderNumber, OrderStatusEnums status) {
        Order order = lambdaQuery()
                .eq(Order::getOrderNumber, orderNumber)
                .one();
        if (Objects.isNull(order)) {
            throw new ApException(ApExceptionEnum.ORDER_STATUS_UPDATE_ERROR_NOT_DATA);
        }

        if (order.getPayStatus().equals(status.getValue())) {
            return;
        }
        order.setPayStatus(status.getValue());

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setUserId(order.getUserId());
        updateOrder.setPayStatus(order.getPayStatus());

        Boolean execute = runUpdate(orderNumber, updateOrder, order);

        if (Boolean.FALSE.equals(execute)) {
            throw new ApException("支付宝回调执行逻辑失败");
        }
    }

    private void saveUserCommodity(Order order) {
        UserCommodity userCommodity = new UserCommodity();
        userCommodity.setCommodityId(order.getCommodityId());
        userCommodity.setUserId(order.getUserId());
        userCommodityService.save(userCommodity);
    }

    @Override
    public IPage<OrderVO> page(OrderListQuery query) {

        return getBaseMapper().page(new Page<OrderVO>(query.getCurrent(), query.getSize()), query);
    }

    @Override
    public PayCommodityVO payOrder(Long commodityId) {
        return getPayCode(commodityId, false);
    }

    private PayCommodityVO getPayCode(Long commodityId, Boolean refresh) {
        AssertUtil.isTrue("0".equals(SecurityUtil.getUserId().toString()), "用户id获取失败！");
        OrderVO orderVO = getBaseMapper().getBy(SecurityUtil.getUserId(), commodityId);
        if (Objects.isNull(orderVO)) {
            throw new ApException(ApExceptionEnum.DATA_ERROR);
        }
        PayCommodityVO vo = new PayCommodityVO();
        vo.setName(orderVO.getCommodityName());
        vo.setPrice(orderVO.getMoney());
        vo.setQrCode(orderVO.getQrCode());
        if (Boolean.TRUE.equals(orderVO.usefulQrCode()) && Boolean.FALSE.equals(refresh)) {
            return vo;
        }

        String qrCode = "";
        // 订单编号
        String orderNumber = SnowFlakeUtil.getId().toString();
        try {
            qrCode = aliPay.getCode(
                    orderNumber,
                    vo.getName(),
                    vo.getPrice().toString(),
                    "",
                    ServerNameConstants.ALI_CALLBACK_PAY_COMMODITY_URL
            );
            vo.setQrCode(qrCode);
        } catch (AlipayApiException e) {
            throw new ApException("获取支付二维码出错！");
        }

        Order order = new Order();
        order.setId(orderVO.getId());
        order.setOrderNumber(orderNumber);
        order.setQrCode(vo.getQrCode());
        order.setExpireTime(LocalDateTime.now().plusHours(1).plusMinutes(30));
        updateById(order);
        return vo;
    }

    @Override
    public PayCommodityVO refreshQrcode(Long commodityId) {
        return getPayCode(commodityId, true);
    }

    @Override
    public void orderStatusCheck() {
        /*
        1、获取所有未支付订单
        2、访问支付宝，获取订单状态
         */
        List<Order> orderList = lambdaQuery().eq(Order::getPayStatus, 0)
                .list();
        orderList.forEach(order -> {
            try {
                if (order.usefulQrCode() && "TRADE_SUCCESS".equals(aliPay.tradeQuery(order.getOrderNumber()))) {
                    log.info("定时任务处理订单状态：{}", order.getId());
                    // for (CallbackHandler handler : callbackHandlerList) {
                    //     if (Boolean.TRUE.equals(handler.isRun(order))){
                    //         handler.callback(order, OrderStatusEnums.PAY_SUCCESS);
                    //     }
                    // }
                    // 注册成功回调
                    if (order.getCallbackUrl().contains("register-success")) {
                        log.info("定时任务处理订单状态-注册成功");
                        registerSuccess(order.getOrderNumber());
                    }
                    // 支付商品成功回调
                    if (order.getCallbackUrl().contains("pay-commodity-success")) {
                        log.info("定时任务处理订单状态-支付商品成功");
                        updateOrderStatus(order.getOrderNumber(), OrderStatusEnums.PAY_SUCCESS);
                    }
                }
            } catch (AlipayApiException e) {
                log.info("定时任务处理订单状态失败：orderId={}", order.getId(), e);
            }
        });


    }

    @Override
    public Boolean registerSuccess(String orderNumber) {
        /**
         * 用户注册支付成功业务
         * 1、修改订单状态
         * 2、feign调用 user 服务生成用户账号信息
         */
        AtomicReference<String> msg = new AtomicReference<>("");
        Boolean execute = transactionTemplate.execute(status -> {
            Boolean result = Boolean.TRUE;
            try {
                // 调用 feign 生成用户信息
                UserVO userVO = userFeign.saveUserByOrderNumber(orderNumber);
                //  修改订单状态，生成流水数据
                updateOrderStatusAndUserId(orderNumber, userVO, OrderStatusEnums.PAY_SUCCESS);
            } catch (Exception e) {
                // 错误处理
                log.error("处理用户支付成功回调失败：", e);
                status.setRollbackOnly();
                result = Boolean.FALSE;
                msg.set(e.getMessage());
            }
            return result;
        });

        if (Boolean.FALSE.equals(execute)) {
            log.info("支付宝支付成功回调...失败");
            throw new ApException(msg.get());
        }
        return execute;
    }
}




