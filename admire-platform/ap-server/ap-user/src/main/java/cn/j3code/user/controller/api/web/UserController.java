package cn.j3code.user.controller.api.web;

import cn.j3code.common.annotation.ResponseResult;
import cn.j3code.config.constant.UrlPrefixConstants;
import cn.j3code.config.util.SecurityUtil;
import cn.j3code.user.controller.api.web.form.RegisterForm;
import cn.j3code.user.controller.api.web.form.UpdateUserForm;
import cn.j3code.user.controller.api.web.vo.RegisterResultVO;
import cn.j3code.user.controller.api.web.vo.UserVO;
import cn.j3code.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.controller
 * @createTime 2023/2/3 - 22:19
 * @description
 */
@Slf4j
@AllArgsConstructor
@ResponseResult
@RequestMapping(UrlPrefixConstants.V1 + "/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @GetMapping("/loginByOrderNumber")
    public String login(@RequestParam("orderNumber") String orderNumber) {
        return userService.login(orderNumber);
    }


    @PostMapping("/register")
    public RegisterResultVO register(@Validated @RequestBody RegisterForm form) {
        return userService.register(form);
    }


    @GetMapping("/getUser")
    public UserVO getUser() {
        return userService.getUser(SecurityUtil.getUserId());
    }

    @PostMapping("/update")
    public void update(@Validated @RequestBody UpdateUserForm form) {
        form.setId(SecurityUtil.getUserId());
        userService.update(form);
    }


    @GetMapping("/logOut")
    public void logOut() {
        userService.logOut();
    }
}
