package cn.j3code.admire.bo;

import cn.j3code.admire.po.Commodity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.bo
 * @createTime 2023/2/4 - 22:19
 * @description
 */
@Data
@Accessors(chain = true)
public class SaveOrderBO {

    private Commodity commodity;

    private String orderNumber;

    private String qrCode;
}
