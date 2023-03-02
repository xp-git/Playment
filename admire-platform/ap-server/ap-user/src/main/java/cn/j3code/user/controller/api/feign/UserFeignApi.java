package cn.j3code.user.controller.api.feign;

import cn.j3code.config.constant.UrlPrefixConstants;
import cn.j3code.config.vo.feign.UserVO;
import cn.j3code.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.controller.api.feign
 * @createTime 2023/2/4 - 23:13
 * @description
 */
@RestController
@RequestMapping(UrlPrefixConstants.V1 + "/feign/user")
@AllArgsConstructor
public class UserFeignApi {

    private final UserService userService;

    @GetMapping("/saveUserByOrderNumber")
    public UserVO saveUserByOrderNumber(@RequestParam("orderNumber") String orderNumber) {
        return userService.saveUserByOrderNumber(orderNumber);
    }


}
