package com.joyone.test.entity;

import java.sql.Blob;

public class SfDocument {
    private String sfid;

    private byte[] body;

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
}
