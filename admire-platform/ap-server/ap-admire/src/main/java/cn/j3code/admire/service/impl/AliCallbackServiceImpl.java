package cn.j3code.admire.service.impl;

import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.pay.AliPay;
import cn.j3code.admire.service.AliCallbackService;
import cn.j3code.admire.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.service.impl
 * @createTime 2023/2/4 - 22:41
 * @description
 */
@Slf4j
@AllArgsConstructor
@Service
public class AliCallbackServiceImpl implements AliCallbackService {

    private final AliPay aliPay;
    private final OrderService orderService;


    @Override
    public void registerSuccess(HttpServletRequest request, HttpServletResponse response) {
        log.info("支付宝支付成功回调...开始");
        aliPay.payCallback(request, response, orderNumber -> {
            Boolean execute = orderService.registerSuccess(orderNumber);
            log.info("支付宝支付成功回调...结束");
        });
    }

    @Override
    public void payCommoditySuccess(HttpServletRequest request, HttpServletResponse response) {
        log.info("支付宝支付成功回调...开始");
        aliPay.payCallback(request, response, orderNumber -> {
            orderService.updateOrderStatus(orderNumber, OrderStatusEnums.PAY_SUCCESS);
            log.info("支付宝支付成功回调...结束");
        });
    }
}
