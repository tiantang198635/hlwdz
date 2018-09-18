package com.ncbx.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/user/")
public class UserController {
	@RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(){
        // Return the token
        return "test";
    }
}
