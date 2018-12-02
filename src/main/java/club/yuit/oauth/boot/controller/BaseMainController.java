package club.yuit.oauth.boot.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yuit
 * @create time 2018/10/9  15:09
 * @description
 * @modify by
 * @modify time
 **/
@Controller
public class BaseMainController {

	@GetMapping("/login")
	public String loginPage(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "login";
	}

}
