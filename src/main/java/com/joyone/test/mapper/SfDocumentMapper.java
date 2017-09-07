package com.joyone.test.mapper;

import com.joyone.test.entity.SfDocument;

import java.util.Map;

public interface SfDocumentMapper {

    SfDocument getSfDocument(String sfid);

    int updateDocument(Map<String,Object> paramMap);
}
