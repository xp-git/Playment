package cn.j3code.config.util;

import cn.j3code.config.exception.ApException;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.config.util
 * @createTime 2022/12/4 - 21:52
 * @description
 */
public class AssertUtil {

    /**
     * 条件是 true，抛出消息
     * @param expression
     * @param message
     */
    public static void isTrue(Boolean expression, String message) {
        if (expression) {
            throw new ApException(message);
        }
    }

    /**
     * 条件是 false，抛出消息
     * @param expression
     * @param message
     */
    public static void isFalse(Boolean expression, String message) {
        if (!expression) {
            throw new ApException(message);
        }
    }
}
