package com.joyone.test.services;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SFAccessTokenService {

    private static String ACCESS_TOKEN = null;

    @Value("${salesforce.authUrl}")
    private String url;

    @Value("${salesforce.clientId}")
    private String clientId;

    @Value("${salesforce.clentSecret}")
    private String clentSecret;

    @Value("${salesforce.redirectUrl}")
    private String redirectUrl;

    @Value("${salesforce.userName}")
    private String userName;

    @Value("${salesforce.password}")
    private String password;

    public String getAccessToken(){
        if(ACCESS_TOKEN == null){
            ACCESS_TOKEN = getAccessTokenFromSalesForce();
            return ACCESS_TOKEN;
        }
        return ACCESS_TOKEN;
    }

    public void refreshAccessToken(){
        ACCESS_TOKEN = getAccessTokenFromSalesForce();
    }

    private String getAccessTokenFromSalesForce(){
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.addParameter("grant_type", "password");
        post.addParameter("client_id",clientId);
        post.addParameter("client_secret", clentSecret);
        post.addParameter("redirect_uri", redirectUrl);
        post.addParameter("username", userName);
        post.addParameter("password", password);
        int returnCode = 0;
        String returnToken=null;
        try {
            returnCode = httpclient.executeMethod(post);
            if(returnCode==200){
                JSONObject jsonResult  = JSONObject.fromObject(post.getResponseBodyAsString());
                returnToken = jsonResult.getString("access_token");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnToken;
    }
}
