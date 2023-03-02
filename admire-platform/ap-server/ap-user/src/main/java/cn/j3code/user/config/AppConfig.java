package cn.j3code.user.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.config
 * @createTime 2023/2/4 - 22:25
 * @description
 */
@Configuration
@EnableFeignClients(basePackages = {"cn.j3code.user.feign"})
public class AppConfig {
}
