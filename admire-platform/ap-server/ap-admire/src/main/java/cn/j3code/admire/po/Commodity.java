package cn.j3code.admire.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName ap_commodity
 */
@TableName(value = "ap_commodity")
@Data
public class Commodity implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 类型（1：收费，2：免费）
     */
    private Integer type;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 描述链接
     */
    private String descriptionUrl;

    /**
     * 付费内容
     */
    private String content;

    /**
     * 购买人数
     */
    private Integer buyNumber;

    /**
     * 查看人数
     */
    private Integer lookNumber;

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
}