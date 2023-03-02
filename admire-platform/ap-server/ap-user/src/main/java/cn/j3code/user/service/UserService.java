package cn.j3code.user.service;

import cn.j3code.config.util.SecurityUtil;
import cn.j3code.user.controller.api.web.form.RegisterForm;
import cn.j3code.user.controller.api.web.form.UpdateUserForm;
import cn.j3code.user.controller.api.web.vo.RegisterResultVO;
import cn.j3code.user.controller.api.web.vo.UserVO;
import cn.j3code.user.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author J3code
 * @description 针对表【ap_user】的数据库操作Service
 * @createDate 2023-02-04 11:33:22
 */
public interface UserService extends IService<User> {

    String login(String username, String password);

    String login(String orderNumber);

    RegisterResultVO register(RegisterForm form);

    UserVO getUser(Long userId);

    default UserVO getUser() {
        return getUser(SecurityUtil.getUserId());
    }

    cn.j3code.config.vo.feign.UserVO saveUserByOrderNumber(String orderNumber);

    void logOut();

    void update(UpdateUserForm form);
}
