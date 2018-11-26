package club.yuit.oauth.boot.controller;

import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuit
 * @create 2018/11/1 16:44
 * @description
 * @modify
 */
@Slf4j
@RestController
public class UserController {

	@GetMapping("/other")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/users/username")
	public UserModel user(//
			@RequestParam(required = false) String username, //
			@RequestParam(required = false) String state, //
			HttpServletRequest servletRequest, //
			Principal principal) {
		UserModel model = new UserModel();
		Enumeration<String> attributeNames = servletRequest.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String string = (String) attributeNames.nextElement();
			log.info(string + ":" + servletRequest.getAttribute(string));
		}
		log.info(principal.toString());
		log.info(username + ",state:" + state);
		model.setUsername(principal.getName());
		return model;
	}

	public static class UserModel {

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		private String username;
	}
}
