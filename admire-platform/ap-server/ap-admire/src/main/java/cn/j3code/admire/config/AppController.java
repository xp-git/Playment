package cn.j3code.admire.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.config
 * @createTime 2023/2/4 - 23:01
 * @description
 */
@EnableScheduling
@Configuration
@EnableTransactionManagement
@EnableFeignClients(basePackages = {"cn.j3code.admire.feign"})
public class AppController {
}
