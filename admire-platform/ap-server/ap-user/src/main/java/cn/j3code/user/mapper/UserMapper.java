package cn.j3code.user.mapper;

import cn.j3code.user.controller.api.web.vo.UserVO;
import cn.j3code.user.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author J3code
* @description 针对表【ap_user】的数据库操作Mapper
* @createDate 2023-02-04 11:33:22
* @Entity cn.j3code.user.po.User
*/
public interface UserMapper extends BaseMapper<User> {

    Long getMinTagByType(@Param("type") int type);

    UserVO getUser(@Param("userId") Long userId);
}




