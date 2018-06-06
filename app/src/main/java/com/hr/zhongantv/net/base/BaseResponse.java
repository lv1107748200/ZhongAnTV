package com.hr.zhongantv.net.base;

/**
 * Created by 吕 on 2017/10/27.
 */

public class BaseResponse<T> {

    private int status;
    private int sysCode;
    private String msg;
    private T data;

    public int getSysCode() {
        return sysCode;
    }

    public void setSysCode(int sysCode) {
        this.sysCode = sysCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
