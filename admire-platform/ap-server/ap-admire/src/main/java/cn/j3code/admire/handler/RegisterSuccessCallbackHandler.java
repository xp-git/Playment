package cn.j3code.admire.handler;

import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.po.Order;
import cn.j3code.admire.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.handler
 * @createTime 2023/2/12 - 23:15
 * @description
 */
@Slf4j
@AllArgsConstructor
@Component
public class RegisterSuccessCallbackHandler implements CallbackHandler{

    private final ApplicationContext applicationContext;

    @Override
    public Boolean isRun(Order order) {
        return order.getCallbackUrl().contains("register-success");
    }

    @Override
    public void callback(Order order, OrderStatusEnums status) {
        OrderService orderService = applicationContext.getBean(OrderService.class);
        orderService.registerSuccess(order.getOrderNumber());
    }
}
