package com.sign.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sign.user.configure.UserConfigure;
import com.sign.util.RedisUtil;

@RestController
@RequestMapping(value="/user/")
public class UserController {
	@Autowired
	private UserConfigure configure;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(HttpServletRequest request){
        // Return the token
		String username = request.getHeader(configure.curUserName);
		redisUtil.set("foo", "test");
        return "test"+username+redisUtil.get("foo");
    }
}
