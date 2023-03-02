package cn.j3code.apgateway.filter;

import cn.j3code.apgateway.handler.AuthHandler;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.exception.ApException;
import cn.j3code.config.vo.FailInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.gateway.filter
 * @createTime 2022/11/30 - 21:51
 * @description
 */
@Slf4j
@Component
@Order(-100)
@Data
@ConfigurationProperties(prefix = "ap.global-filter")
public class TokenAuthGlobalFilter implements GlobalFilter {

    private final ObjectMapper objectMapper;
    private final AuthHandler authHandler;

    public TokenAuthGlobalFilter(ObjectMapper objectMapper, AuthHandler authHandler) {
        this.objectMapper = objectMapper;
        this.authHandler = authHandler;
    }

    /**
     * 忽略认证url
     */
    private Set<String> ignoreUrlSet = Set.of(
            "user/login",
            "user/register"
    );

    /**
     * 认证标识
     */
    private String authorization = "Authorization";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求url
        // http://127.0.0.1:7210/ld-lucky/v1/user/login
        String url = exchange.getRequest().getURI().getPath();

        // 忽略认证
        for (String ignoreUrl : ignoreUrlSet) {
            if (Boolean.TRUE.equals(ignore(url, ignoreUrl))) {
                return chain.filter(exchange);
            }
        }

        // 认证逻辑
        String token = exchange.getRequest().getHeaders().getFirst(authorization);
        ServerHttpResponse resp = exchange.getResponse();

        try {
            Map<String, Object> userMap = authHandler.auth(token);
            ServerHttpRequest.Builder mutate = exchange.getRequest().mutate();
            mutate.header("name", URLEncoder.encode(Objects.isNull(userMap.get("name")) ? "" : userMap.get("name").toString()), "UTF-8");
            mutate.header("nickname", URLEncoder.encode(Objects.isNull(userMap.get("nickname")) ? "" : userMap.get("nickname").toString()), "UTF-8");
            mutate.header("id", Objects.isNull(userMap.get("id")) ? "0" : userMap.get("id").toString());
            mutate.header("token", token);
            return chain.filter(exchange.mutate().request(mutate.build()).build());
        } catch (Exception e) {
            //错误处理
            log.error("token认证失败：", e);
            // 写一个统一错误JSON出去
            return autoError(resp, ApExceptionEnum.TOKEN_AUTH_ERROR.getDescription());
        }
    }

    private Mono<Void> autoError(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(new FailInfo(msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));

        return resp.writeWith(Flux.just(buffer));
    }


    /**
     * 忽略逻辑
     * 1、判断字符串存在（当前使用）
     * 2、用正则
     * 3、相似度算法（99%）
     *
     * @param url       ：请求url
     * @param ignoreUrl ： 忽略url
     * @return
     */
    private Boolean ignore(String url, String ignoreUrl) {
        if (Objects.isNull(url)) {
            throw new ApException("请求 url 有误！");
        }
        return url.contains(ignoreUrl);
    }

}
