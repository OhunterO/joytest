package com.joyone.test.controller;

import com.joyone.test.entity.SfDocument;
import com.joyone.test.mapper.SfDocumentMapper;
import com.joyone.test.services.SFAccessTokenService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/test")
public class TestController {

    private String testStr=null;

    @Autowired
    private SFAccessTokenService sFAccessTokenService;

    @Autowired
    private SfDocumentMapper sfDocumentMapper;

    @RequestMapping(method = GET)
    @ResponseBody
    public String test(HttpServletRequest request){
        String code = request.getParameter("code");
        if(code!=null){
            testStr = code;
        }
        if(testStr!=null){
            return testStr;
        }
        return "test success";
    }

    @RequestMapping(value="index",method = GET)
    public String testPage(){
        System.out.println("aaC1122..");
        return "test/index";
    }

    @RequestMapping(value="refresh",method = GET)
    @ResponseBody
    public String refreshToken(){
        sFAccessTokenService.refreshAccessToken();
        return "refresh OK";
    }

    @ResponseBody
    @RequestMapping(value="/getdupic",method = GET)
    public String getPicFromDocument(ServletResponse response) throws IOException{
        SfDocument document = sfDocumentMapper.getSfDocument("0157F000000HxpXQAS");
        System.out.println(document.getSfid());
        System.out.println(document.getBody());
        byte[] picByte = document.getBody();
        System.out.println("size=="+picByte.length);
        ServletOutputStream outputStream = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {

            outputStream = response.getOutputStream();
            byte[] b = decoder.decodeBuffer(new String(picByte));
            outputStream.write(b);
            outputStream.flush();
        } catch (IOException e) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }

        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value="/getpic",method = GET)
    public String getPic(ServletResponse response) throws IOException {
        response.setContentType("image/" + "png");

        HttpClient httpclient = new HttpClient();
        String accessToken=sFAccessTokenService.getAccessToken();
        GetMethod post = new GetMethod("https://ap5.salesforce.com/servlet/servlet.FileDownload?file=0157F000000HxphQAC");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        int returnCode = httpclient.executeMethod(post);
        System.out.println("returnCode " + returnCode);
        System.out.println(post.getResponseBodyAsString());
        InputStream is = post.getResponseBodyAsStream();

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[10000];
            int i = -1;
            while ((i = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }

            outputStream.flush();
        } catch (IOException e) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        return null;
    }

}
