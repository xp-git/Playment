package cn.j3code.user.controller.api.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.controller.api.web.vo
 * @createTime 2023/2/7 - 23:18
 * @description
 */
@Data
public class UpdateUserForm{
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
    @NotNull(message = "性别不为空！")
    private Integer sex;

    /**
     * 个性签名
     */
    private String signature;
}
