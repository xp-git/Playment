package cn.j3code.admire.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName ap_order
 */
@TableName(value = "ap_order")
@Data
public class Order implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long commodityId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 回调url
     */
    private String callbackUrl;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 支付渠道（支付宝、微信）
     */
    private Integer payType;

    /**
     * 支付状态（0：未付款，1：已付款）
     */
    private Integer payStatus;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 支付二维码
     */
    private String qrCode;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 二维码是否有用，true有效，false无效
     * @return
     */
    public Boolean usefulQrCode() {
        return expireTime.isAfter(LocalDateTime.now());
    }

}