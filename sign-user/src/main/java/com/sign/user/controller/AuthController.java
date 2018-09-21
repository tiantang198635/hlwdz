package com.sign.user.controller;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sign.entity.User;
import com.sign.user.configure.UserConfigure;
import com.sign.user.service.AuthService;
import com.sign.vo.JwtAuthenticationRequest;
import com.sign.vo.JwtAuthenticationResponse;

@RestController
public class AuthController {
	@Autowired
	private UserConfigure configure;
    
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,HttpServletResponse response) throws AuthenticationException{
        String token = authService.login(authenticationRequest.getName(), authenticationRequest.getPwd());
        if(StringUtils.isNotEmpty(token)) {
        	//将token设置到客户端的cookies中
        	Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(configure.maxAge);
            cookie.setDomain(configure.domain);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
    

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(configure.tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }
}
