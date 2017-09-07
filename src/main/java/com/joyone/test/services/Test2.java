package com.joyone.test.services;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;

public class Test2 {
    public static void main(String[] args) throws HttpException, IOException {
        HttpClient httpclient = new HttpClient();

        JSONObject content = new JSONObject();
        content.put("id", "123445aa");
        String accessToken="00D7F000001ca8T!AQwAQAniLizKxDDUprauX.bvWRzmu7NPhXd451ZOhVwu4v2350jEBBwebWxrXligVxbJflZf2qEB2o8ocNhzmeTC.K4bLtWB";
        PostMethod post = new PostMethod("https://ap5.salesforce.com/services/apexrest/rest");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        post.addParameter("id","12234");
        post.setRequestEntity(new StringRequestEntity(content.toString(),"application/json", null));
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());

    }
}
