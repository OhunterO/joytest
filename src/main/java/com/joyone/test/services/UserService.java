package com.joyone.test.services;


import com.joyone.test.entity.User;
import com.joyone.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> userList(){
        Map<String,Object> map=new HashMap<String, Object>();
        return userMapper.selectByMap(map);
    }

    public void addUser(User user){
        userMapper.insert(user);
    }

}
