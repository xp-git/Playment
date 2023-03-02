package cn.j3code.admire.service;

import cn.j3code.config.vo.feign.UserRegisterVO;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.service
 * @createTime 2023/2/4 - 20:44
 * @description
 */
public interface PayRegisterService {

    UserRegisterVO userPayRegister(String callbackUrl);

}
