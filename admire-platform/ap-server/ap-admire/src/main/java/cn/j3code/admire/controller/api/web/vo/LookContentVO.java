package cn.j3code.admire.controller.api.web.vo;

import lombok.Data;

/**
 * @author J3
 * @package cn.j3code.admire.controller.api.web.vo
 * @createTime 2023/2/7 - 12:23
 * @description \
 */
@Data
public class LookContentVO {
    private String content;
    private Boolean pay;
    private Boolean existOrder;
}
