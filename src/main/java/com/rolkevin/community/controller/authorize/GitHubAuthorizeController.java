package com.rolkevin.community.controller.authorize;

import com.rolkevin.community.dto.AccessTokenDTO;
import com.rolkevin.community.dto.GithubUser;
import com.rolkevin.community.mapper.UserMapper;
import com.rolkevin.community.model.User;
import com.rolkevin.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 调用GitHub登录认证接口授权成功之后的回调地址，用于接收code，state
 */
@Controller
public class GitHubAuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/authcallback")
    public String oauthCallBack(@RequestParam(name="code") String code,
                                @RequestParam(name="state")String state,
                                HttpServletRequest request,
                                HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId,
                clientSecret,
                code,
                redirectUri,
                state);
        //String token = gitHubProvider.getAccessToken(new AccessTokenDTO("","","","","",""));
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitHubProvider.getGithubUser(token);
        //System.out.println(githubUser.toString());
        if(githubUser!=null){
            User oldUser = userMapper.selectUserByAccountid(githubUser.getId());
            if (oldUser!=null){
                setCookie(response,oldUser.getToken());
                return "redirect:/";
            }
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token1 = UUID.randomUUID().toString();
            user.setToken(token1);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
            setCookie(response,token1);
            //response.addCookie(new Cookie("token",token1));
            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }

    }

    private void setCookie(HttpServletResponse response,String value){
        response.addCookie(new Cookie("token",value));
    }
}
