package cn.j3code.apgateway.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.gateway.resolver
 * @createTime 2022/11/30 - 23:33
 * @description
 */
public class IpKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        //return Mono.just(exchange.getRequest().getPath().value());
        return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
