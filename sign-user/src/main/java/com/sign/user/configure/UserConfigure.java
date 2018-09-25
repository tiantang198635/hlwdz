package com.sign.user.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sign.interceptor.TokenHandlerInterceptor;
@Configuration
public class UserConfigure implements WebMvcConfigurer  {
	@Value("${jwt.tokenHead}")
	public String tokenHead;

	@Value("${jwt.header}")
	public String tokenHeader;

    @Value("${context.domain}")
    public String domain;
    
    @Value("${cookie.maxage}")
    public int maxAge;
    
    //存放到头部的用户名名称
    @Value("${header.username}")
    public String curUserName;
    
    /**
     * 上传文件保存的目录
     */
    @Value("${upload.folder}")
    public String UPLOADED_FOLDER;
    
    @Autowired
    private TokenHandlerInterceptor tokenHandlerInterceptor;
    
    /**
     * 添加拦截器，不对auth开头的URL（登录或注册）拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/**");
    }
}
