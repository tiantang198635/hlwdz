package com.business.center.base.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/account/")
public class AccountController {
	@RequestMapping(value = "info", method = RequestMethod.POST)
    public String info(String account,HttpServletRequest request){
        return account+" info";
    }
}
