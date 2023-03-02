package cn.j3code.admire.handler;

import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.po.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.handler
 * @createTime 2023/2/12 - 23:17
 * @description
 */
@Slf4j
@AllArgsConstructor
@Component
public class UpdateOrderStatusCallbackHandler implements CallbackHandler{
    @Override
    public Boolean isRun(Order order) {
        return null;
    }

    @Override
    public void callback(Order order, OrderStatusEnums status) {

    }
}
