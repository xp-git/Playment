package cn.j3code.config.dto;

import lombok.Data;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.config.dto
 * @createTime 2023/2/5 - 11:36
 * @description
 */
@Data
public class QueryPage {
    private Long size = 10L;
    private Long current = 1L;
}
