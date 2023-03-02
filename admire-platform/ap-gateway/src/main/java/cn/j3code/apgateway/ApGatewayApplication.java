package cn.j3code.apgateway;

import cn.j3code.apgateway.resolver.IpKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApGatewayApplication.class, args);
	}

	@Bean("ipKeyResolver")
	public IpKeyResolver ipKeyResolver(){
		return new IpKeyResolver();
	}
}
