
package cn.fintechstar.exchange.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @auther: Water
* @time: 27 Feb 2018 17:09:42
* 
*/

@Controller
public class IndexController {
	
	@RequestMapping("/")
	@ResponseBody
	public String index(){
		return "exchange security srv index !";
	}
	
}
