package cn.j3code.user.enums;

import lombok.Getter;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.enums
 * @createTime 2023/2/4 - 14:10
 * @description
 */
@Getter
public enum RegisterTypeEnum {

    NO_PUBLIC_REGIST(1, "公众号链接注册"),
    PAY_REGISTER(2, "支付注册"),

    ;

    private Integer value;

    private String description;

    RegisterTypeEnum(Integer value, String description){
        this.value = value;
        this.description = description;
    }
}
