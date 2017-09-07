package com.joyone.test.controller;

import com.joyone.test.entity.SfDocument;
import com.joyone.test.mapper.SfDocumentMapper;
import com.joyone.test.services.SFAccessTokenService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
        //System.out.println(document.getSfid());
        //System.out.println(document.getBody());
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
        GetMethod post = new GetMethod("https://c.ap5.content.force.com/profilephoto/7297F000000L4NQ/T/1");
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

    @RequestMapping(value="/updatePic",method = POST)
    public String updatePic(@RequestParam(value = "uploadFile") MultipartFile uploadFile){
        System.out.println("update...");
        try {
            String fileName = uploadFile.getOriginalFilename();
            String type= fileName.substring(fileName.lastIndexOf("."),fileName.length());
            System.out.println("fileName=="+fileName);
            System.out.println("type=="+type);
            InputStream is=uploadFile.getInputStream();
            byte[] fileByte = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = is.read(b)) != -1){
                bos.write(b, 0, n);
            }
            is.close();
            bos.close();
            fileByte = bos.toByteArray();
            String fileStr = new String(Base64.encodeBase64(fileByte));
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("name",fileName);
            paramMap.put("type",type);
            paramMap.put("body",fileStr.getBytes());
            paramMap.put("sfid","0157F000000HxpXQAS");
            sfDocumentMapper.updateDocument(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/test/getdupic";
    }
}