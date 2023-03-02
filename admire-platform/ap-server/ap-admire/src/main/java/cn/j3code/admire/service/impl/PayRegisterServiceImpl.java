package cn.j3code.admire.service.impl;

import cn.j3code.admire.bo.SaveOrderBO;
import cn.j3code.admire.po.Commodity;
import cn.j3code.admire.service.CommodityService;
import cn.j3code.admire.service.OrderService;
import cn.j3code.admire.service.PayRegisterService;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.exception.ApException;
import cn.j3code.config.vo.feign.UserRegisterVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.service.impl
 * @createTime 2023/2/4 - 20:44
 * @description
 */
@Slf4j
@AllArgsConstructor
@Service
public class PayRegisterServiceImpl implements PayRegisterService {

    private final CommodityService commodityService;
    private final OrderService orderService;

    @Override
    public UserRegisterVO userPayRegister(String callbackUrl) {
        // 写死获取支付注册商品
        Commodity commodity = commodityService.lambdaQuery()
                .eq(Commodity::getId, 1L)
                .one();

        if (Objects.isNull(commodity)) {
            throw new ApException(ApExceptionEnum.PAY_REGISTER_DATA_ERROR);
        }

        // 生成订单
        SaveOrderBO saveOrderBO = orderService.saveByCommodity(commodity, callbackUrl);

        return new UserRegisterVO()
                .setPrice(commodity.getPrice())
                .setCommodityName(commodity.getName())
                .setQrCode(saveOrderBO.getQrCode());
    }
}
