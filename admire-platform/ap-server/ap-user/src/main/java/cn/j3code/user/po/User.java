package cn.j3code.user.po;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @TableName ap_user
 */
@TableName(value = "ap_user")
@Data
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 订单编号（付款注册用户，可通过交易商户订单号直接登录，商户订单号可在支付宝交易记录中查看）
     */
    private String orderNumber;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像:url
     */
    private String headPicture;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 注册类型（1：公众号/链接，2：付款注册）
     */
    private Integer registerType;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     *
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


    public void setPassword(String password) {
        if (Objects.isNull(password)) {
            return;
        }
        // 密码 md5 加密
        this.password = MD5.create().digestHex(password);
    }
}