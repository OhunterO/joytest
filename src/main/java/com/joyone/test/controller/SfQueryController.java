package com.joyone.test.controller;

import com.joyone.test.mapper.SfQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/sfquery")
public class SfQueryController {

    @Autowired
    private SfQueryMapper sfQueryMapper;

    @RequestMapping(value ="/count",method = GET)
    @ResponseBody
    public String getTestInfo(){
        long start = System.currentTimeMillis();
        Integer count = sfQueryMapper.testInfoCount();
        long end = System.currentTimeMillis();
        return "total==="+count+",time=="+(end-start);
    }
}
