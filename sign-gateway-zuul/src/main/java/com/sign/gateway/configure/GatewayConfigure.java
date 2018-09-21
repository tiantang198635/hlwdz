package com.sign.gateway.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfigure {
	@Value("${jwt.header}")
    public String tokenHeader;

    @Value("${jwt.tokenHead}")
    public String tokenHead;
    
    @Value("${jwt.secret}")
    public String secret;
    
    //存放到头部的用户名名称
    @Value("${header.username}")
    public String curUserName;
}