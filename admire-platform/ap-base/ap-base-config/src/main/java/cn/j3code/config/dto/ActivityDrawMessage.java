package cn.j3code.config.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.config.dto
 * @createTime 2022/12/8 - 23:24
 * @description
 */
@Data
@Accessors(chain = true)
public class ActivityDrawMessage {

    /**
     * 业务唯一id
     */
    private Long id;
    private String uuid;

    /**
     * JSON内容对象
     */
    private String body;
}
