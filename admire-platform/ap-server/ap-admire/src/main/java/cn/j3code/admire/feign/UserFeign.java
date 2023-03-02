package cn.j3code.admire.feign;

import cn.j3code.config.constant.ServerNameConstants;
import cn.j3code.config.vo.feign.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.feign
 * @createTime 2023/2/4 - 23:17
 * @description
 */
@FeignClient(value = ServerNameConstants.USER, path = "/v1/feign/user")
public interface UserFeign {

    @GetMapping("/saveUserByOrderNumber")
    UserVO saveUserByOrderNumber(@RequestParam("orderNumber") String orderNumber);

}
