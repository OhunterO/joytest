package com.joyone.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/test")
public class TestController {

    @RequestMapping(method = GET)
    @ResponseBody
    public String test(){
        return "test success";
    }

    @RequestMapping(value="index",method = GET)
    public String testPage(){
        return "test/index";
    }

}
