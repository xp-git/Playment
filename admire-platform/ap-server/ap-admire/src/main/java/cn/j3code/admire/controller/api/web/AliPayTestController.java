package cn.j3code.admire.controller.api.web;

import cn.hutool.core.util.RandomUtil;
import cn.j3code.admire.pay.AliPay;
import cn.j3code.common.annotation.ResponseResult;
import cn.j3code.config.constant.UrlPrefixConstants;
import com.alipay.api.AlipayApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.controller
 * @createTime 2023/2/4 - 0:18
 * @description
 */
@Slf4j
@AllArgsConstructor
@ResponseResult
@RequestMapping(UrlPrefixConstants.V1 + "/test")
public class AliPayTestController {

    private final AliPay aliPay;


    @GetMapping("/getCode")
    public String getCode() throws AlipayApiException {

        return aliPay.getCode(
                RandomUtil.randomString(12),
                "三哥商品测试",
                "0.1",
                "测试支付", null);
    }



    @GetMapping("/tradeQuery")
    public String tradeQuery(@RequestParam("outTradeNo") String outTradeNo) throws AlipayApiException {
        return aliPay.tradeQuery(outTradeNo);
    }


    @PostMapping("/payCallback")
    public void payCallback(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        aliPay.payCallback(request, response, outTradeNo -> {
            log.info("支付成功！outTradeNo = {}", outTradeNo);
        });
    }
}
