package com.joyone.test.mapper;

import com.joyone.test.entity.TestPart;
import com.joyone.test.entity.User;

import java.util.List;

public interface TestPartMapper {
    int insertTestPart(List<TestPart> testPartList);
}
