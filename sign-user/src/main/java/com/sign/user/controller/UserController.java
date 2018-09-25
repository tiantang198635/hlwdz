package com.sign.user.controller;

 

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sign.user.configure.UserConfigure;
import com.sign.user.feign.AccountServiceFeign;
import com.sign.util.RedisUtil;

@RestController
@RequestMapping(value="/user/")
public class UserController {
	@Autowired
	private UserConfigure configure;
	
	@Autowired
	private AccountServiceFeign accountServiceFeign;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(HttpServletRequest request){
        // Return the token
		String username = request.getHeader(configure.curUserName);
		//redisUtil.set("foo", "test");
        //return "test"+username+redisUtil.get("foo")+","+accountServiceFeign.account(username);
		return this.accountServiceFeign.account(username);
    }
	
	@PostMapping("/upload") 
	public String singleFileUpload(@RequestParam("file") MultipartFile file,
	                               RedirectAttributes redirectAttributes) {
	    if (file.isEmpty()) {
	        redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	        return "redirect:uploadStatus";
	    }

	    try {
	        // Get the file and save it somewhere
	        byte[] bytes = file.getBytes();
	        Path path = Paths.get(configure.UPLOADED_FOLDER + "images"+File.separator+file.getOriginalFilename());
	        Files.write(path, bytes);

	        redirectAttributes.addFlashAttribute("message",
	                "You successfully uploaded '" + file.getOriginalFilename() + "'");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return "redirect:/uploadStatus";
	}
}
