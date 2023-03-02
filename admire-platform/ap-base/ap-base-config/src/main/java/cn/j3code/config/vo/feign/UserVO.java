package cn.j3code.config.vo.feign;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.config.vo.feign
 * @createTime 2023/2/5 - 0:12
 * @description
 */
@Data
@Accessors(chain = true)
public class UserVO {
    private Long id;
}
