package com.joyone.test.services;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import java.io.File;
import java.io.IOException;

public class PhotoUploadTest {
    public static void main(String[] args) throws HttpException, IOException {
        File contentFile = new File("c:/up.png");
        Part[] parts = {new FilePart("fileUpload", contentFile)};
        HttpClient httpclient = new HttpClient();
        String accessToken="00D7F000001ca8T!AQwAQOSIq6pkid1Kw3_HIhQlDDTaLC2bkYHCCgns40VVDRF7t21YaooNA93EFOc3ldy3t.x_iUo1NZoijoD_hv5KQiEeuaBI";
        PostMethod post = new PostMethod("https://ap5.salesforce.com/services/data/v23.0/chatter/users/me/photo");
        post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());

    }
}
