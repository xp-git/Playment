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
public class CommodityListQuery extends QueryPage {

    private Integer type;

    private Long userId;

    /**
     * 是否购买：1：已购买商品，2未购买商品
     */
    private Integer isPay;

    private String name;
}
