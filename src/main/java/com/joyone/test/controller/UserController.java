package com.joyone.test.controller;

import com.joyone.test.entity.User;
import com.joyone.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    public List<User> userList(){
        System.out.println("search......");
        return userService.userList();
    }

    @RequestMapping(value ="/count",method = GET)
    public String count(){
        long start = System.currentTimeMillis();
        List<User> list=userService.userList();
        long end = System.currentTimeMillis();
        return list==null?"0":(list.size()+"")+",time=="+(end-start);
    }

    @RequestMapping(value ="/add",method = GET)
    public String addUser(){
        User user = new User();
        user.setName("fanyo");
        user.setAge(20);
        userService.addUser(user);
        return "add OK!";
    }

    @RequestMapping(value ="/addmany",method = GET)
    public String addManyUser(){
        userService.addManyUser();
        return "add OK!";
    }

    @RequestMapping(value ="/addtestpart",method = GET)
    public String addTestPart(){
        userService.addTestPart();
        return "add OK!";
    }
}
