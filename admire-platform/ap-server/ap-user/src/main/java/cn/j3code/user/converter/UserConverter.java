package cn.j3code.user.converter;

import cn.j3code.user.controller.api.web.form.UpdateUserForm;
import cn.j3code.user.controller.api.web.vo.UserVO;
import cn.j3code.user.po.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.user.converter
 * @createTime 2023/2/4 - 15:23
 * @description
 */
@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)//交给spring管理
public interface UserConverter {

    UserVO converter(User byId);

    User converter(UpdateUserForm form);
}
