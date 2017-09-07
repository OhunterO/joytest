package com.joyone.test.services;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.*;

public class UpdateFileTest {

    public  static void main(String[] args) throws HttpException, IOException {
        File contentFile = new File("C:/pi.png");

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
        content.put("Name", "pi");
        content.put("Type", "png");
        HttpClient httpclient = new HttpClient();
        String accessToken="00D7F000001ca8T!AQwAQLBx8TSGHndf2mT0fJHWcOonOk3UJu6kcuwvX._7Ko5BilLneNLVpLwyXonrExIirci7buDXA5bMxgukOnsMl7serxNu";
        PostMethod post = new PostMethod("https://ap5.salesforce.com/services/data/v23.0/Document/0157F000000HxpXQAS");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        post.setRequestEntity(new StringRequestEntity(content.toString(),"application/json", null));
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());
    }
}
