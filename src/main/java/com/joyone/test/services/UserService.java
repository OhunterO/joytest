package com.joyone.test.services;


import com.joyone.test.entity.SfUser;
import com.joyone.test.entity.TestPart;
import com.joyone.test.entity.User;
import com.joyone.test.mapper.SfQueryMapper;
import com.joyone.test.mapper.TestPartMapper;
import com.joyone.test.mapper.TestUserMapper;
import com.joyone.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SfQueryMapper sfQueryMapper;

    @Autowired
    private TestUserMapper testUserMapper;

    @Autowired
    private TestPartMapper testPartMapper;

    public List<User> userList(){
        Map<String,Object> map=new HashMap<String, Object>();
        //List<SfUser> list = sfQueryMapper.getSfUser();
        //System.out.println("siezz==="+list.size());
        //for (SfUser user:list) {
        //    System.out.println("sfid"+user.getSfid());
        //}
        return userMapper.selectByMap(map);
    }

    public void addUser(User user){
        userMapper.insert(user);
    }


    public void addManyUser(){
        userMapper.deleteSql("delete from testuser");
        List<User> userList = new ArrayList<User>();
        for (int i=0;i<=11000;i++){
            User user = new User();
            user.setName("fanyo_"+i);
            user.setAge(20);
            userList.add(user);
        }
        testUserMapper.insertUsert(userList);

    }

    public void addTestPart(){
        List<TestPart> testPartList = new ArrayList<TestPart>();
        for (int i=0;i<=11000;i++){
            TestPart testPart = new TestPart();
            testPart.setName("part_"+i);
            if(i>5000){
                testPart.setCreatedate(new Date());
            }else{
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date  date = null;
                try {
                    date = format.parse("2016-11-04");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                testPart.setCreatedate(date);
            }

            testPartList.add(testPart);
        }
        testPartMapper.insertTestPart(testPartList);

    }

}
