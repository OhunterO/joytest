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
        File contentFile = new File("D:/asd.png");

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
        content.put("Name", "testFile");
        content.put("Type", "png");
//        Part[] parts = {
//                new StringPart("Type","png"),
//                new StringPart("FolderId","005D0000001GiU7"),
//                new FilePart("fileUpload", contentFile)};
        HttpClient httpclient = new HttpClient();
        String accessToken="00D7F000001ca8T!AQwAQOSIq6pkid1Kw3_HIhQlDDTaLC2bkYHCCgns40VVDRF7t21YaooNA93EFOc3ldy3t.x_iUo1NZoijoD_hv5KQiEeuaBI";
        PostMethod post = new PostMethod("https://ap5.salesforce.com/services/data/v23.0/sobjects/Document/");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
//        post.addRequestHeader("Content-Disposition","name=\"Body\"; filename=\"asd.png\"");
//        post.addRequestHeader("Content-Type","image/png");
        //post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
        post.setRequestEntity(new StringRequestEntity(content.toString(),"application/json", null));

        //post.addParameter("Type","png");
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());
    }
}
