package com.rolkevin.community.controller.authorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用GitHub登录认证接口授权成功之后的回调地址，用于接收scope
 */
@Controller
public class GitHubAuthorizeController {
    @GetMapping("/authcallback")
    public String oauthCallBack(@RequestParam(name="code") String code,
                                @RequestParam(name="state")String state, Model model){
        System.out.println(code+">>>"+state);
        return "index";
    }
}
