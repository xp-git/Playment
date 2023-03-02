package cn.j3code.user.controller.api.web.vo;

import cn.j3code.user.po.User;
import lombok.Data;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.controller.vo
 * @createTime 2023/2/4 - 13:05
 * @description
 */
@Data
public class UserVO extends User {
    private String tagName;
}
