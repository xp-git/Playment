package cn.j3code.apgateway.handler;

import cn.j3code.config.constant.ServerNameConstants;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.exception.TokenAuthException;
import cn.j3code.config.util.ApUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author J3
 * @package cn.j3code.apgateway.util
 * @createTime 2023/2/3 - 17:10
 * @description
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> auth(String token) {
        String key = ApUtil.loginRedisKey(token);
        Object obj = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(obj)) {
            throw new TokenAuthException(ApExceptionEnum.TOKEN_AUTH_ERROR.getDescription());
        }

        // 自动续约
        redisTemplate.expire(key, ServerNameConstants.AUTH_KEY_EXPIRED_TIME);
        return JSONObject.parseObject(obj.toString(), Map.class);
    }
}
