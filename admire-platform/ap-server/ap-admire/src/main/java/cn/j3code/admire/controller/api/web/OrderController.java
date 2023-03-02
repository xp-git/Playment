package cn.j3code.admire.controller.api.web;

import cn.j3code.admire.controller.api.web.query.OrderListQuery;
import cn.j3code.admire.controller.api.web.vo.OrderVO;
import cn.j3code.admire.controller.api.web.vo.PayCommodityVO;
import cn.j3code.admire.service.OrderService;
import cn.j3code.common.annotation.ResponseResult;
import cn.j3code.config.constant.UrlPrefixConstants;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.util.SecurityUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.api.web
 * @createTime 2023/2/5 - 14:17
 * @description
 */
@Slf4j
@AllArgsConstructor
@ResponseResult
@RequestMapping(UrlPrefixConstants.V1 + "/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/page")
    public IPage<OrderVO> page(OrderListQuery query) {
        query.setUserId(Objects.requireNonNull(SecurityUtil.getUserId(), ApExceptionEnum.DATA_ERROR.getDescription()));
        return orderService.page(query);
    }

    @GetMapping("/pay-order")
    public PayCommodityVO payOrder(@RequestParam("commodityId") Long commodityId) {
        return orderService.payOrder(commodityId);
    }

    @GetMapping("/refresh-qrcode")
    public PayCommodityVO refreshQrcode(@RequestParam("commodityId") Long commodityId) {
        return orderService.refreshQrcode(commodityId);
    }

}
