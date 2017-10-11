package com.joyone.test.mapper;

import com.joyone.test.entity.SfUser;
import com.joyone.test.entity.TestInfo;

import java.util.List;

public interface SfQueryMapper {

    List<SfUser> getSfUser();

    Integer testInfoCount();

    List<TestInfo> findOneTestInfo(String name);
}
