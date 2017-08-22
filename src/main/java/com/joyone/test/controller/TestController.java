package com.joyone.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value="/test")
public class TestController {

    @RequestMapping(method = GET)
    public String test(){
        return "test success";
    }
}
