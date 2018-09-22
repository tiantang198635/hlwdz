package com.sign.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用account的服务接口
 * @author lafei
 *
 */
@FeignClient(value = "base-account")
public interface AccountServiceFeign {
	@RequestMapping(value = "/account/info",method = RequestMethod.POST)
    public String account(@RequestParam(value = "account") String account);
}
