package cn.j3code.admire.pay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.pay
 * @createTime 2023/2/3 - 22:13
 * @description
 */
@Slf4j
@Component
@AllArgsConstructor
public class AliPay {

    private final MyAliPayConfig myAliPayConfig;

    /**
     * 支付二维码
     * https://opendocs.alipay.com/open/02ekfg?scene=19#%E8%AF%B7%E6%B1%82%E7%A4%BA%E4%BE%8B
     *
     * @param outTradeNo  商品编号，唯一
     * @param subject     商品名称
     * @param totalAmount 金额
     * @param body        其它参数
     * @return
     * @throws AlipayApiException
     */
    public String getCode(String outTradeNo, String subject, String totalAmount, String body,
                          String callbackUrl) throws AlipayApiException {
        log.info("支付请求参数：outTradeNo={}，subject={}，totalAmount={}，body={}", outTradeNo, subject, totalAmount, body);
        //统一收单线下交易预创建
        AlipayTradePrecreateRequest payRequest = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setBody(body);
        payRequest.setBizModel(model);
        // 异步回调url
        payRequest.setNotifyUrl(myAliPayConfig.getPayCallbackUrl());
        if (Objects.nonNull(callbackUrl)) {
            payRequest.setNotifyUrl(callbackUrl);
        }
        AlipayTradePrecreateResponse response = myAliPayConfig.getAlipayClient().execute(payRequest);
        log.info("支付请求结果：{}", JSON.toJSONString(response));
        return response.getQrCode();
    }

    /**
     * 查询订单支付状态
     *
     * @param outTradeNo 商品编号，唯一
     * @return
     * @throws AlipayApiException
     */
    public String tradeQuery(String outTradeNo) throws AlipayApiException {
        log.info("订单编号：{}", outTradeNo);
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
        queryModel.setOutTradeNo(outTradeNo);
        queryRequest.setBizModel(queryModel);
        AlipayTradeQueryResponse queryResponse = myAliPayConfig.getAlipayClient().execute(queryRequest);
        log.info("订单信息：{}", JSON.toJSONString(queryResponse));
        return queryResponse.getTradeStatus();
    }

    /**
     * https://opendocs.alipay.com/open/194/103296
     * https://blog.csdn.net/weixin_44004020/article/details/111472797
     *
     * @param request
     * @param response
     * @param callback 支付宝成功的业务逻辑
     */
    public void payCallback(HttpServletRequest request, HttpServletResponse response, Consumer<String> callback) {
        try (PrintWriter out = response.getWriter()) {
            Map<String, String[]> requestParams = request.getParameterMap();
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            // 循环遍历支付宝请求过来的参数存入到params中
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            System.out.println(JSON.toJSONString(params));
            // 异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, myAliPayConfig.getAlipayPublicKey(), "utf-8", "RSA2");
            if (flag) {
                // 说明是支付宝调用的本接口
                if (params.get("trade_status").equals("TRADE_SUCCESS") || params.get("trade_status").equals("TRADE_FINISHED")) {
                    System.out.println("收到回调结果，用户已经完成支付，订单号：" + params.get("out_trade_no"));
                    // 异步执行业务逻辑代码
                    //new Thread(() -> callback.accept(params.get("out_trade_no"))).start();
                    callback.accept(params.get("out_trade_no"));
                    out.write("success");
                }
            } else {
                // 验签失败该接口被别人调用
                out.write("支付宝异步回调验签失败，请留意!");
            }
        } catch (Exception e) {
            // 错误处理
            log.error("执行异步出错，", e);
        }
    }
}