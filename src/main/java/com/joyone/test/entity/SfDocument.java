package com.joyone.test.entity;

import java.sql.Blob;

public class SfDocument {
    private String sfid;

    private byte[] body;

    private String name;

    private String type;

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
