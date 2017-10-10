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

}
