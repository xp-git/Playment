package cn.j3code.config.util;

import java.util.Map;
import java.util.Objects;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.config.util
 * @createTime 2022/11/30 - 22:45
 * @description
 */
public class SecurityUtil {

    private static ThreadLocal<Map<String, Object>> userThreadLocal = new ThreadLocal<>();

    public static ThreadLocal<Map<String, Object>> getUserThreadLocal() {
        return userThreadLocal;
    }

    public static void addConfig(Map<String, Object> user) {
        userThreadLocal.set(user);
    }

    public static void remove() {
        userThreadLocal.remove();
    }


    public static String getName() {
        Object name = userThreadLocal.get().get("name");

        return Objects.isNull(name) ? "" : name.toString();
    }

    public static String getNickname() {
        Object nickname = userThreadLocal.get().get("nickname");

        return Objects.isNull(nickname) ? "" : nickname.toString();
    }

    public static Long getUserId() {
        Object userId = userThreadLocal.get().get("id");

        return Objects.isNull(userId) ? 0L : Long.parseLong(userId.toString());
    }

    public static String get(String key) {
        Object value = userThreadLocal.get().get(key);

        return Objects.isNull(value) ? "" : value.toString();
    }

    public static String getToken() {
        Object token = userThreadLocal.get().get("token");

        return Objects.isNull(token) ? "" : token.toString();
    }

}
