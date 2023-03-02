package cn.j3code.user.feign;

import cn.j3code.config.constant.ServerNameConstants;
import cn.j3code.config.vo.feign.UserRegisterVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.feign
 * @createTime 2023/2/4 - 22:24
 * @description
 */
@FeignClient(value = ServerNameConstants.ASMIRE, path = "/v1/feign/user")
public interface UserFeign {

    @GetMapping("/userPayRegister")
    UserRegisterVO userPayRegister(@RequestParam("callbackUrl") String callbackUrl);

}
