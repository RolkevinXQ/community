package com.rolkevin.community.provider;

import com.alibaba.fastjson.JSON;
import com.rolkevin.community.dto.AccessTokenDTO;
import com.rolkevin.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    @Value("${github.access.url}")
    private String accessUrl;
    @Value("${github.user.url}")
    private String userUrl;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //final String accessUrl = "https://github.com/login/oauth/access_token";
        String content = JSON.toJSONString(accessTokenDTO);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(accessUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String access = response.body().string();
            String token = access.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken){
        //final String userUrl = "https://api.github.com/user?access_token=";
        System.out.println("accessToken="+accessToken);
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(userUrl+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String userResponse = response.body().string();
                GithubUser githubUser = JSON.parseObject(userResponse, GithubUser.class);
                return githubUser;
                //return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

}
