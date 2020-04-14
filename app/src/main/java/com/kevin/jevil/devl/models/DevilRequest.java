package com.kevin.jevil.devl.models;

public class DevilRequest {
    private int code;
    private long elapsedTime;
    private String url;
    private String size;
    private long serverReqResTime;

    public DevilRequest(int code, long elapsedTime, String url, String size, long serverReqResTime) {
        this.code = code;
        this.elapsedTime = elapsedTime;
        this.url = url;
        this.size = size;
        this.serverReqResTime = serverReqResTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getServerReqResTime() {
        return serverReqResTime;
    }

    public void setServerReqResTime(long serverReqResTime) {
        this.serverReqResTime = serverReqResTime;
    }
}
