package cn.j3code.admire.enums;

import lombok.Getter;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.enums
 * @createTime 2023/2/5 - 13:18
 * @description
 */
@Getter
public enum CommodityTypeEnum {
    CHARGE(1, "收费"),
    FREE(2, "免费"),

    ;
    private Integer value;

    private String description;

    CommodityTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
