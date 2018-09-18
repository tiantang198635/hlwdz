package com.ncbx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * Token认证拦截器
 * @author win7
 *
 */
@Component
public class TokenHandlerInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(TokenHandlerInterceptor.class);
	 //在请求处理之前进行调用（Controller方法调用之前
	@Value("${header.username}")
    public String curUserName;
	
	/**
	 * 从请求头获取用户名，如果没有获取到表示没有登录，则被拦截，返回错误信息
	 */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    	String username = httpServletRequest.getHeader(curUserName);
    	if(StringUtils.isEmpty(username)) {
    		JSONObject json = new JSONObject();
    		json.put("code", HttpServletResponse.SC_UNAUTHORIZED);
    		json.put("msg", "sorry you have not login or invalid,please login");
    		httpServletResponse.getWriter().write(json.toJSONString());
    		logger.error("not login or invalid,{}",httpServletRequest.getServletPath());
    		return false;
    	}
    	logger.debug("preHandle被调用,{}",httpServletRequest.getServletPath());
        return true;    //如果false，停止流程，api被拦截
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle被调用");
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug("afterCompletion被调用");
    }
}
