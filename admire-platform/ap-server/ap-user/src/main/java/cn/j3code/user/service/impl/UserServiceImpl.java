package cn.j3code.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.j3code.config.constant.ServerNameConstants;
import cn.j3code.config.enums.ApExceptionEnum;
import cn.j3code.config.exception.ApException;
import cn.j3code.config.exception.LoginAuthException;
import cn.j3code.config.util.ApUtil;
import cn.j3code.config.util.AssertUtil;
import cn.j3code.config.util.SecurityUtil;
import cn.j3code.config.vo.feign.UserRegisterVO;
import cn.j3code.user.controller.api.web.form.RegisterForm;
import cn.j3code.user.controller.api.web.form.UpdateUserForm;
import cn.j3code.user.controller.api.web.vo.RegisterResultVO;
import cn.j3code.user.controller.api.web.vo.UserVO;
import cn.j3code.user.converter.UserConverter;
import cn.j3code.user.enums.RegisterTypeEnum;
import cn.j3code.user.feign.UserFeign;
import cn.j3code.user.mapper.UserMapper;
import cn.j3code.user.po.User;
import cn.j3code.user.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author J3code
 * @description 针对表【ap_user】的数据库操作Service实现
 * @createDate 2023-02-04 11:33:22
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserConverter userConverter;
    private final UserFeign userFeign;

    @Override
    public String login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return auth(
                lambdaQuery().eq(User::getUsername, user.getUsername())
                        .eq(User::getPassword, user.getPassword())
                        .one()
        );
    }

    @Override
    public String login(String orderNumber) {
        return auth(
                lambdaQuery().eq(User::getOrderNumber, orderNumber)
                        .one()
        );
    }

    private String auth(User user) {
        if (Objects.isNull(user)) {
            throw new LoginAuthException(ApExceptionEnum.LOGIN_ERROR.getDescription());
        }
        String tokenStr = ApUtil.getTokenStr();
        redisTemplate.opsForValue()
                .set(ApUtil.loginRedisKey(tokenStr), JSON.toJSONString(user), ServerNameConstants.AUTH_KEY_EXPIRED_TIME);
        return tokenStr;
    }

    @Override
    public RegisterResultVO register(RegisterForm form) {
        //  默认注册
        if (RegisterTypeEnum.NO_PUBLIC_REGIST.getValue().equals(form.getType())) {
            return noPublicRegister(form);
        }
        //  支付注册
        if (RegisterTypeEnum.PAY_REGISTER.getValue().equals(form.getType())) {
            return payRegister(form);
        }
        //  报错
        throw new ApException(ApExceptionEnum.REGISTER_TYPE_ERROR);
    }

    @Override
    public UserVO getUser(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ApException("用户 id 不可为空！");
        }

        return getBaseMapper().getUser(userId);
    }


    private RegisterResultVO payRegister(RegisterForm form) {
        /**
         * 付款注册
         * 1、获取支付二维码
         * 2、返回二维码
         */
        UserRegisterVO userRegisterVO = userFeign.userPayRegister(ServerNameConstants.ALI_CALLBACK_USER_REGISTER_URL);

        if (StringUtils.isBlank(userRegisterVO.getQrCode())) {
            throw new ApException(ApExceptionEnum.REGISTER_DATA_ERROR);
        }

        return new RegisterResultVO()
                .setCommodityName(userRegisterVO.getCommodityName())
                .setPrice(userRegisterVO.getPrice())
                .setQrCode(userRegisterVO.getQrCode());
    }

    private RegisterResultVO noPublicRegister(RegisterForm form) {
        // 判断账号密码是否为空
        if (Objects.isNull(form.getUsername()) || Objects.isNull(form.getPassword())) {
            throw new ApException(ApExceptionEnum.REGISTER_DATA_ERROR);
        }
        // 判断账号是否存在
        AssertUtil.isTrue(lambdaQuery().eq(User::getUsername, form.getUsername()).count() > 0, "账号存在！");

        // 填充user对象
        User user = new User();
        user.setNickname("默认注册-" + RandomUtil.randomNumbers(5));
        user.setPassword(form.getPassword());
        user.setUsername(form.getUsername());
        user.setRegisterType(RegisterTypeEnum.NO_PUBLIC_REGIST.getValue());
        // 类型（1:商品、2:用户）
        user.setTagId(getBaseMapper().getMinTagByType(2));
        // 保存
        save(user);
        // 返回结果信息
        return new RegisterResultVO().setUsername(user.getUsername());
    }


    @Override
    public cn.j3code.config.vo.feign.UserVO saveUserByOrderNumber(String orderNumber) {
        // 生成支付注册用户信息
        User user = new User();
        user.setOrderNumber(orderNumber);
        user.setNickname("支付注册-" + RandomUtil.randomNumbers(5));
        user.setRegisterType(RegisterTypeEnum.PAY_REGISTER.getValue());
        user.setTagId(2L);
        save(user);
        // 返回用户id
        return new cn.j3code.config.vo.feign.UserVO()
                .setId(user.getId());
    }

    @Override
    public void logOut() {
        if (Boolean.FALSE.equals(redisTemplate.delete(ApUtil.loginRedisKey(SecurityUtil.getToken())))) {
            throw new ApException(ApExceptionEnum.LOGOUT_ERROR);
        }
    }

    @Override
    public void update(UpdateUserForm form) {
        AssertUtil.isTrue(lambdaQuery()
                .ne(User::getId, form.getId())
                .eq(User::getUsername, form.getUsername()).count() > 0, "账号存在！");

        User user = userConverter.converter(form);
        updateById(user);
    }
}




