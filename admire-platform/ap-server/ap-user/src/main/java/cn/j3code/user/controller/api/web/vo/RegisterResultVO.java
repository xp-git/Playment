package cn.j3code.user.controller.api.web.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.controller.vo
 * @createTime 2023/2/4 - 12:10
 * @description
 */
@Data
@Accessors(chain = true)
public class RegisterResultVO {
    private String username;

    private String orderNumber;

    private String commodityName;

    private BigDecimal price;

    /**
     * 支付二维码
     */
    private String qrCode;
}
