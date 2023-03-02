package cn.j3code.admire.controller.api.web.vo;

import cn.j3code.admire.po.Order;
import lombok.Data;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.api.web.vo
 * @createTime 2023/2/5 - 14:18
 * @description
 */
@Data
public class OrderVO extends Order {
    private String commodityName;
}
