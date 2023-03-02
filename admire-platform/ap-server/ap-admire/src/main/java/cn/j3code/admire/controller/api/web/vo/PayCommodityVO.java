package cn.j3code.admire.controller.api.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author J3
 * @package cn.j3code.admire.controller.api.web.vo
 * @createTime 2023/2/7 - 14:15
 * @description
 */
@Data
public class PayCommodityVO {
    private String name;
    private BigDecimal price;
    private String qrCode;
}
