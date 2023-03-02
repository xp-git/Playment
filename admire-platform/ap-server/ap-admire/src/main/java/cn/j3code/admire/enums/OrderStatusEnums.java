package cn.j3code.admire.enums;

import lombok.Getter;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.enums
 * @createTime 2023/2/4 - 23:26
 * @description
 */
@Getter
public enum OrderStatusEnums {

    NOT_PAY(0, "未付款"),
    PAY_SUCCESS(1, "已付款"),

    ;
    private Integer value;

    private String description;

    OrderStatusEnums(Integer value, String description){
        this.value = value;
        this.description = description;
    }

}
