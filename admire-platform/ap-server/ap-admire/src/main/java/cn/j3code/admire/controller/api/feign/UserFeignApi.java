package cn.j3code.admire.controller.api.feign;

import cn.j3code.admire.service.PayRegisterService;
import cn.j3code.config.constant.UrlPrefixConstants;
import cn.j3code.config.vo.feign.UserRegisterVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.feign
 * @createTime 2023/2/4 - 15:37
 * @description
 */
@RestController
@RequestMapping(UrlPrefixConstants.V1 + "/feign/user")
@AllArgsConstructor
public class UserFeignApi {

    private final PayRegisterService payRegisterService;

    @GetMapping("/userPayRegister")
    public UserRegisterVO userPayRegister(@RequestParam("callbackUrl") String callbackUrl){
        //UserRegisterVO userRegisterVO = new UserRegisterVO();
        //userRegisterVO.setQrCode("https://qr.alipay.com/bax03818bmpsuldewdeh0052");
        //userRegisterVO.setPrice(new BigDecimal("1.00"));
        //userRegisterVO.setCommodityName("注册商品");
        //return userRegisterVO;
        return payRegisterService.userPayRegister(callbackUrl);
    }




}
