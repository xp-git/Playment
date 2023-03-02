package cn.j3code.admire.controller.api.web.vo;

import cn.j3code.admire.po.Commodity;
import lombok.Data;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller.api.web.vo
 * @createTime 2023/2/5 - 11:33
 * @description
 */
@Data
public class CommodityVO extends Commodity {
    private String tagName;
}
