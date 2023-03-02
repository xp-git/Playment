package cn.j3code.common.annotation;

import java.lang.annotation.*;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.annotation
 * @createTime 2022/12/12 - 23:01
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DistributedLock {

    String key() default "distributedLock";

    long expiredTime() default 30L;

    int maxToRenewNum() default 30;
}
