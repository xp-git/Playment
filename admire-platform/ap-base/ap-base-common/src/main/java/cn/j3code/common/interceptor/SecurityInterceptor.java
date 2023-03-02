package cn.j3code.common.interceptor;

import cn.j3code.config.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.interceptor
 * @createTime 2022/11/30 - 23:13
 * @description
 */
@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> userMap = new HashMap<>();

        String name = URLDecoder.decode(Objects.isNull(request.getHeader("name")) ? "" : request.getHeader("name"), "UTF-8");
        String nickname = URLDecoder.decode(Objects.isNull(request.getHeader("nickname")) ? "" : request.getHeader("nickname"), "UTF-8");
        String id = request.getHeader("id");
        String token = request.getHeader("token");
        String authorization = request.getHeader("Authorization");

        userMap.put("name", name);
        userMap.put("authorization", authorization);
        userMap.put("nickname", nickname);
        userMap.put("id", id);
        userMap.put("token", token);

        SecurityUtil.addConfig(userMap);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityUtil.remove();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
