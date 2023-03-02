package cn.j3code.admire.service;

import cn.j3code.admire.bo.SaveOrderBO;
import cn.j3code.admire.controller.api.web.query.OrderListQuery;
import cn.j3code.admire.controller.api.web.vo.OrderVO;
import cn.j3code.admire.controller.api.web.vo.PayCommodityVO;
import cn.j3code.admire.enums.OrderStatusEnums;
import cn.j3code.admire.po.Commodity;
import cn.j3code.admire.po.Order;
import cn.j3code.config.vo.feign.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author J3code
 * @description 针对表【ap_order】的数据库操作Service
 * @createDate 2023-02-04 12:01:46
 */
public interface OrderService extends IService<Order> {


    Order getOrderBy(Long commodityId, Long userId);

    void updateOrderStatusAndUserId(String orderNumber, UserVO userVO, OrderStatusEnums status);

    /**
     * 根据商品，插入订单，并返回支付二维码
     * @param commodity
     * @return
     */
    SaveOrderBO saveByCommodity(Commodity commodity, String callbackUrl);

    void updateOrderStatus(String orderNumber, OrderStatusEnums paySuccess);

    IPage<OrderVO> page(OrderListQuery query);

    PayCommodityVO payOrder(Long commodityId);

    PayCommodityVO refreshQrcode(Long commodityId);

    void orderStatusCheck();


    Boolean registerSuccess(String orderNumber);

}
