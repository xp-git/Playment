package cn.j3code.admire.controller.api.web.query;

import cn.j3code.config.dto.QueryPage;
import lombok.Data;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.api.web.query
 * @createTime 2023/2/5 - 11:35
 * @description
 */
@Data
public class OrderListQuery extends QueryPage {

    private Long userId;
    private Long commodityId;
    private String commodityName;

    private String orderNumber;

    private Integer payStatus;
}
