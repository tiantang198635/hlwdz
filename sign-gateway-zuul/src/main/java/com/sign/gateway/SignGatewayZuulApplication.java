package com.sign.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
//@ComponentScan(basePackages={"com.ncbx"})
public class SignGatewayZuulApplication {
	public static void main(String[] args) {
        SpringApplication.run( SignGatewayZuulApplication.class, args );
    }
}
