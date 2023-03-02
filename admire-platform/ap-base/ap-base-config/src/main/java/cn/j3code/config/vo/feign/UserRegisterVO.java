package cn.j3code.config.vo.feign;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.feign.vo
 * @createTime 2023/2/4 - 15:39
 * @description
 */
@Data
@Accessors(chain = true)
public class UserRegisterVO {

    private String commodityName;

    private BigDecimal price;

    /**
     * 支付二维码
     */
    private String qrCode;
}
