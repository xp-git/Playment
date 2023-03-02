package cn.j3code.user.controller.api.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.controller.form
 * @createTime 2023/2/4 - 12:13
 * @description
 */
@Data
public class RegisterForm {

    private String username;

    private String password;

    /**
     * 1：正常注册，2：付款注册
     */
    @NotNull(message = "注册类型不为空！")
    private Integer type;

}
