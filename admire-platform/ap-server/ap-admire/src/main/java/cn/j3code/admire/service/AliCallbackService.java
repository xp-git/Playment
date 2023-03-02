package cn.j3code.admire.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.service
 * @createTime 2023/2/4 - 22:41
 * @description
 */
public interface AliCallbackService {
    void registerSuccess(HttpServletRequest request, HttpServletResponse response);

    void payCommoditySuccess(HttpServletRequest request, HttpServletResponse response);
}
