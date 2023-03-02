package cn.j3code.common.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.config
 * @createTime 2022/11/26 - 21:51
 * @description
 */
@Slf4j
@Configuration
@MapperScan("cn.j3code.*.mapper")
public class MapperScanConfig {
}
