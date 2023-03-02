package cn.j3code.config.util;

import cn.hutool.core.lang.UUID;
import cn.j3code.config.constant.ServerNameConstants;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.config.util
 * @createTime 2023/2/4 - 13:41
 * @description
 */
public class ApUtil {


    public static String getTokenStr() {
        return UUID.randomUUID().toString().replaceAll("-", "") + UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String loginRedisKey(String token) {
        return ServerNameConstants.AUTH_KEY + token;
    }

}
