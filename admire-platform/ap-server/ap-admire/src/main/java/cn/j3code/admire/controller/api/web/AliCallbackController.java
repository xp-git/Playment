package cn.j3code.admire.controller.api.web;

import cn.j3code.admire.service.AliCallbackService;
import cn.j3code.common.annotation.ResponseResult;
import cn.j3code.config.constant.UrlPrefixConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.api.web
 * @createTime 2023/2/4 - 22:39
 * @description
 */
@Slf4j
@AllArgsConstructor
@ResponseResult
@RequestMapping(UrlPrefixConstants.V1 + "/ali-callback")
public class AliCallbackController {

    private final AliCallbackService aliCallbackService;

    @PostMapping("/register-success")
    public void registerSuccess(HttpServletRequest request, HttpServletResponse response){
        aliCallbackService.registerSuccess(request, response);
    }
    @PostMapping("/pay-commodity-success")
    public void payCommoditySuccess(HttpServletRequest request, HttpServletResponse response){
        aliCallbackService.payCommoditySuccess(request, response);
    }
}
