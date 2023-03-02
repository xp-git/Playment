package cn.j3code.config.enums;

import cn.j3code.config.vo.FailInfo;
import lombok.Getter;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.config
 * @createTime 2022/12/4 - 21:58
 * @description
 */
@Getter
public enum ApExceptionEnum {

    DATA_ERROR(FailInfo.DEFAULT_CODE, "数据出错，请微信【13207920596】联系三哥！"),

    ADD_ERROR(FailInfo.DEFAULT_CODE, "保存数据失败！"),
    NOT_DATA_ERROR(FailInfo.DEFAULT_CODE, "数据不存在！"),

    UPDATE_ERROR(FailInfo.DEFAULT_CODE, "修改数据失败！"),

    LOGIN_ERROR(FailInfo.DEFAULT_CODE, "登录失败，用户不存在！"),

    LOGOUT_ERROR(FailInfo.DEFAULT_CODE, "退出登录失败！"),

    REGISTER_TYPE_ERROR(FailInfo.DEFAULT_CODE, "注册类型错误！"),

    REGISTER_DATA_ERROR(FailInfo.DEFAULT_CODE, "注册数据不可为空！"),

    TOKEN_AUTH_ERROR(FailInfo.DEFAULT_CODE, "认证失败，请登录！"),
    PAY_REGISTER_DATA_ERROR(FailInfo.DEFAULT_CODE, "支付注册数据不存在，注册失败！"),

    ORDER_STATUS_UPDATE_ERROR_NOT_DATA(FailInfo.DEFAULT_CODE, "订单状态修改失败，数据不存在！"),

    PROMPT_LOGIN_ACCESS(FailInfo.DEFAULT_CODE, "请登录再访问！"),

    ;
    private Integer code;

    private String description;

    ApExceptionEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

}
