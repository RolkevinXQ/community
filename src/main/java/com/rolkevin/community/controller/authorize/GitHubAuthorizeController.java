package com.rolkevin.community.controller.authorize;

import com.rolkevin.community.dto.AccessTokenDTO;
import com.rolkevin.community.dto.GithubUser;
import com.rolkevin.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用GitHub登录认证接口授权成功之后的回调地址，用于接收code，state
 */
@Controller
public class GitHubAuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/authcallback")
    public String oauthCallBack(@RequestParam(name="code") String code,
                                @RequestParam(name="state")String state, Model model){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO("a9f956c1568d02cbd7d6",
                "5e973c560bfcfd196db8839b8806bb66ef739735",
                code,
                "http://localhost:8087/authcallback",
                state);
        //String token = gitHubProvider.getAccessToken(new AccessTokenDTO("","","","","",""));
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitHubProvider.getGithubUser(token);
        System.out.println(githubUser.toString());
        return "index";
    }
}
