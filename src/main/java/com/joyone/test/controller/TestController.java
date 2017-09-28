package com.joyone.test.controller;

import com.joyone.test.entity.SfDocument;
import com.joyone.test.mapper.SfDocumentMapper;
import com.joyone.test.services.EmailService;
import com.joyone.test.services.FileTest;
import com.joyone.test.services.SFAccessTokenService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private EmailService eEmailService;

    @Autowired
    private FileTest fileTest;

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

    @RequestMapping(value="/testpost",method = POST)
    @ResponseBody
    public String testPost(HttpServletRequest request){
        String picStr = request.getParameter("picStr");
        if(picStr!=null){
            return "result:"+picStr;
        }
        return "result:noPic";
    }

    @RequestMapping(value="index",method = GET)
    public String testPage(){
        System.out.println("aaC1122..");
        return "test/index";
    }

    @RequestMapping(value="updemo",method = GET)
    public String testPageDemo(){
        System.out.println("aaC1122..");
        return "test/updemo";
    }

    @RequestMapping(value="createfile",method = GET)
    @ResponseBody
    public String createFile(){
        fileTest.createFile();
        fileTest.writeTxt();
        return "createOK";
    }

    @RequestMapping(value="readfile",method = GET)
    @ResponseBody
    public String readFile(){
        return fileTest.readTxt();
    }

    @RequestMapping(value="nowtime",method = GET)
    @ResponseBody
    public String nowTime(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = format.format(new Date());
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        return time+"   "+timeZone;
    }

    @RequestMapping(value="/uploddemo",method = POST)
    @ResponseBody
    public String updatePicBySsdf(@RequestParam(value = "images") MultipartFile uploadFile){
        System.out.println("update...");
        try {
            String fileName = uploadFile.getOriginalFilename();
            String type= fileName.substring(fileName.lastIndexOf("."),fileName.length());
            System.out.println("fileNameAAA=="+fileName);
            System.out.println("typeVVV=="+type);
            if (!uploadFile.isEmpty()) {
                eEmailService.sendEmail("AAA","BBB");
                try {
                    String newfileName = "rty.jpg";
                    byte[] bytes = uploadFile.getBytes();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(new File("F:/jar/" + newfileName)));
                    buffStream.write(bytes);
                    buffStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "You failed to upload " + fileName + ": " + e.getMessage();
                }
            } else {
                return "Unable to upload. File is empty.";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value="refresh",method = GET)
    @ResponseBody
    public String refreshToken(){
        sFAccessTokenService.refreshAccessToken();
        return "refresh OK";
    }

    /**
     * 直接读取映射过来的Document获取Body来显示图片
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/getdupic",method = GET)
    public String getPicFromDocument(@RequestParam(value = "id",required = false)String id, ServletResponse response) throws IOException{
        String reId= "0157F000000HxpXQAS";
        if(id!=null){
            reId = id;
        }
        SfDocument document = sfDocumentMapper.getSfDocument(reId);
        System.out.println("sfid=="+document.getSfid());
        System.out.println("name=="+document.getName());
        System.out.println("type=="+document.getType());
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

    /**
     * 获取图片，调用SF的自定义Rest api来获取
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/getPicFromSf/{id}",method = GET)
    public String getPicFromDocumentApi(@PathVariable(value = "id")String id, ServletResponse response) throws IOException{
        HttpClient httpclient = new HttpClient();
        String accessToken=sFAccessTokenService.getAccessToken();
        JSONObject content = new JSONObject();
        content.put("id", id);
        PostMethod post = new PostMethod("https://ap5.salesforce.com/services/apexrest/rest");
        post.addRequestHeader("Authorization", "OAuth "+accessToken);
        post.setRequestEntity(new StringRequestEntity(content.toString(),"application/json", null));
        int returnCode = httpclient.executeMethod(post);
        //System.out.println("returnCode " + returnCode);
        //System.out.println(post.getResponseBodyAsString());
        String picStr = post.getResponseBodyAsString();
        picStr=picStr.replace("\"","");
        BASE64Decoder decoder = new BASE64Decoder();
        ServletOutputStream outputStream = null;
        try {

            outputStream = response.getOutputStream();
            byte[] b = decoder.decodeBuffer(picStr);
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

    /**
     * 查看SF的User表里的头像
     * @param response
     * @return
     * @throws IOException
     */
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

    /**
     * 直接更新映射过来的Document,但是有问题
     *
     * @param uploadFile
     * @return
     */
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

    /**
     * 通过SF现有接口，更新文件
     * @param uploadFile
     * @return
     */
    @RequestMapping(value="/updatePicSf",method = POST)
    public String updatePicBySf(@RequestParam(value = "uploadFile") MultipartFile uploadFile,@RequestParam(value = "documentSfId") String documentSfId){
        System.out.println("update...");
        System.out.println("documentSfId=="+documentSfId);
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

            JSONObject content = new JSONObject();
            content.put("Body", fileStr);
            content.put("FolderId", "0057F000000ebtGQAQ");
            content.put("Name", "pi");
            content.put("Type", "png");
            HttpClient httpclient = new HttpClient();
            String accessToken= sFAccessTokenService.getAccessToken();
            PostMethod post = new PostMethod("https://ap5.salesforce.com/services/data/v23.0/sobjects/Document/"+documentSfId){
                @Override
                public String getName() { return "PATCH"; }
            }
                    ;
            post.addRequestHeader("Authorization", "OAuth "+accessToken);
            post.setRequestEntity(new StringRequestEntity(content.toString(),"application/json", null));
            int returnCode = httpclient.executeMethod(post);

            System.out.println("returnCode " + returnCode);
            System.out.println(post.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/test/getPicFromSf/"+documentSfId;
    }

}