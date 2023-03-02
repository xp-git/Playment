package cn.j3code.admire.handler;

import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.po.Order;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.handler
 * @createTime 2023/2/12 - 23:13
 * @description
 */
public interface CallbackHandler {

    Boolean isRun(Order order);

    void callback(Order order, OrderStatusEnums status);

}
