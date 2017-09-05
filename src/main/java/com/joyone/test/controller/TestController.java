package com.joyone.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/test")
public class TestController {
    private String testStr=null;
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

}
