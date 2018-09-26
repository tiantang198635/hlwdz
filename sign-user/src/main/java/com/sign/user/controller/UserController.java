package com.sign.user.controller;

 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sign.http.response.ErrorCodeEnum;
import com.sign.http.response.ResponseResult;
import com.sign.user.configure.UserConfigure;
import com.sign.user.feign.AccountServiceFeign;
import com.sign.util.DateUtil;
import com.sign.util.FileUtil;
import com.sign.util.RedisUtil;

@RestController
@RequestMapping(value="/user/")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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
	
	/**
	 * 上传用户头像
	 * @param file
	 * @return
	 */
	@PostMapping("/upload") 
	public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file) {
		ErrorCodeEnum errorEnum = ErrorCodeEnum.SUCCESS;
	    if (file.isEmpty()) { 
	    	//上传文件为空
	    	errorEnum = ErrorCodeEnum.UploadFileEmpty;
	        return ResponseEntity.ok(new ResponseResult(errorEnum.respCode,errorEnum.explainCn));
	    }
	    
	    String contentType = file.getContentType(); 
	    if(contentType.indexOf("image")==-1) {
	    	//上传文件类型必须为图片
	    	errorEnum = ErrorCodeEnum.UploadFileTypeError;
	        return ResponseEntity.ok(new ResponseResult(errorEnum.respCode,errorEnum.explainCn));
	    }
	    try { 
	    	String todayStr = DateUtil.getTodateString("yyyyMMdd");
	    	
	    	//生成新的文件名
	    	String fileName = file.getOriginalFilename();
	    	fileName = System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
	    	 
	        Path path = FileUtil.createDirectOrFile(configure.getUploadUserHeaderFloder()+todayStr, fileName);
	        
	        //将文件内容写入文件中
	        Files.write(path, file.getBytes());
	        
	        //返回结果状态和图片路径
	        return ResponseEntity.ok(new ResponseResult(errorEnum.respCode,todayStr+"/"+fileName));
	    } catch (IOException e) { 
	        logger.error("singleFileUpload error:{}",e.getMessage());
        	errorEnum = ErrorCodeEnum.SystemError;
	    }
	    return ResponseEntity.ok(new ResponseResult(errorEnum.respCode,errorEnum.explainCn));
	}
}
