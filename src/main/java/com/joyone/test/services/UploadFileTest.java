package com.joyone.test.services;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.*;

public class UploadFileTest {

    public  static void main(String[] args) throws HttpException, IOException {
        File contentFile = new File("D:/pi.png");

        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(contentFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String fileStr = new String(Base64.encodeBase64(buffer));
        JSONObject content = new JSONObject();
        content.put("Body", fileStr);
        content.put("FolderId", "0057F000000ebtGQAQ");
        content.put("Name", "opp");
        content.put("Type", "png");
        HttpClient httpclient = new HttpClient();
        String accessToken="00D7F000001ca8T!AQwAQAniLizKxDDUprauX.bvWRzmu7NPhXd451ZOhVwu4v2350jEBBwebWxrXligVxbJflZf2qEB2o8ocNhzmeTC.K4bLtWB";
        PostMethod post = new PostMethod("https://ap5.salesforce.com/services/data/v23.0/sobjects/Document/");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        post.setRequestEntity(new StringRequestEntity(content.toString(),"application/json", null));
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());
    }
}
