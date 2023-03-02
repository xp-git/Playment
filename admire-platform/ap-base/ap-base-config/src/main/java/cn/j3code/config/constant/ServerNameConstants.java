package cn.j3code.config.constant;

import java.time.Duration;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.config.constant
 * @createTime 2022/12/30 - 22:59
 * @description
 */
public class ServerNameConstants {

    /**
     * 缓存相关
     */
    public final static String SYS = "admire-platform";

    public final static String AUTH_KEY = SYS + ":login:";
    public final static Duration AUTH_KEY_EXPIRED_TIME = Duration.ofDays(2);


    public final static String COMMODITY_LOOK_NUMBER_KEY = SYS + ":commodity:look:";

    /**
     * 服务名称相关
     */
    public final static String USER = "ap-user";

    public final static String ASMIRE = "ap-admire";


    /**
     * 其它
     */
    public final static String HOST = "http://admire.j3code.cn/admire";
    public final static String ALI_CALLBACK_USER_REGISTER_URL = HOST + "/ap-admire/v1/ali-callback/register-success";
    public final static String ALI_CALLBACK_PAY_COMMODITY_URL = HOST + "/ap-admire/v1/ali-callback/pay-commodity-success";
}
