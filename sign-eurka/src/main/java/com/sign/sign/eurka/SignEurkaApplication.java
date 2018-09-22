package com.sign.sign.eurka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SignEurkaApplication {
	public static void main(String[] args) {
        SpringApplication.run( SignEurkaApplication.class, args );
    }
}
