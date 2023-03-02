package cn.j3code.admire.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author <a href="https://j3code.cn">J3code</a>
 * @package cn.j3code.admire.pay
 * @createTime 2023/2/3 - 22:13
 * @description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ali.pay.config")
public class MyAliPayConfig {
    private volatile AlipayClient alipayClient;
    private String serverUrl;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String payCallbackUrl;

    public AlipayClient getAlipayClient() {
        if (Objects.isNull(alipayClient)) {
            synchronized (this) {
                if (Objects.isNull(alipayClient)) {
                    AlipayConfig alipayConfig = new AlipayConfig();
                    alipayConfig.setServerUrl(Objects.requireNonNull(serverUrl, "支付宝网关不可为空！"));
                    alipayConfig.setAppId(Objects.requireNonNull(appId, "应用ID不可为空！"));
                    alipayConfig.setPrivateKey(Objects.requireNonNull(privateKey, "应用私钥不可为空！"));
                    alipayConfig.setAlipayPublicKey(Objects.requireNonNull(alipayPublicKey, "支付宝公钥不可为空！"));
                    alipayConfig.setFormat("JSON");
                    alipayConfig.setCharset("UTF-8");
                    alipayConfig.setSignType("RSA2");
                    try {
                        alipayClient = new DefaultAlipayClient(alipayConfig);
                    } catch (Exception e) {
                        //错误处理
                        e.printStackTrace();
                    }
                }
            }
        }
        return alipayClient;
    }
}
