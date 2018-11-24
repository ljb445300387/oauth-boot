package club.yuit.oauth.boot.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuit
 * @create 2018/11/1 16:44
 * @description
 * @modify
 */
@RestController
public class TestController {

    @GetMapping("/other")
    public Principal user(Principal user) {
        return user;
    }
}
