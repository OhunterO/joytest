package com.joyone.test.services;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class Test {
//    public static void main(String[] args) throws HttpException,  IOException {
//        HttpClient httpclient = new HttpClient();
//        PostMethod post = new PostMethod("https://login.salesforce.com/services/oauth2/token");
//        post.addParameter("grant_type", "password");
//        post.addParameter("client_id",
//                "3MVG9d8..z.hDcPJ.efHV2IeaZwwM.lgkFh5mfxLEw6UuUgOi3GQEacJb5JJ2DeUmYRq819U.mvZTWFzTYUoB");
//        post.addParameter("client_secret", "2569337032767558367");
//        post.addParameter("redirect_uri", "https://joytone.herokuapp.com/joytest/test");
//        post.addParameter("username", "shicr@qq.com");
//        post.addParameter("password", "SHI12345");
//        int returnCode = httpclient.executeMethod(post);
//        System.out.println("returnCode " + returnCode);
//        System.out.println(post.getResponseBodyAsString());
//
//    }
    public static void main(String[] args) throws HttpException,  IOException {
        HttpClient httpclient = new HttpClient();
        String accessToken="00D7F000001ca8T!AQwAQFfV0l6QhcGQTk_NvBQyNdhRDJ3vOgeo71kaCtEULbqSjEOeKJfuytq0U5wdExuFwflasHGMqudKttvxEWdDh2TUqtd.";
        GetMethod post = new GetMethod("https://c.ap5.content.force.com/profilephoto/7297F000000kFJB/T/1");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());
    }
}
