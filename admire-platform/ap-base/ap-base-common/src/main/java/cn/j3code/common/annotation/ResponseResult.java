package cn.j3code.common.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.annotation
 * @createTime 2022/11/26 - 15:42
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@RestController // 组合
public @interface ResponseResult {
    // 是否忽略
    boolean ignore() default false;
}
