package club.yuit.oauth.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import club.yuit.oauth.boot.support.BootSecurityProperties;

/**
 * @author yuit
 * @create time 2018/10/9  15:09
 * @description
 * @modify by
 * @modify time
 **/
@Controller
public class BaseMainController {

	@Autowired
	private BootSecurityProperties properties;

	@GetMapping("/auth/login")
	public String loginPage(Model model) {
		model.addAttribute("loginProcessUrl", properties.getLoginProcessUrl());
		return "base-login";
	}

}
