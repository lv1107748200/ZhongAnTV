package com.hr.zhongantv.net.entry;

/**
 * Created by 吕 on 2018/5/9.
 */

public class LiveData{
    private String liveName;
    private String watchAddress;
    private String beginTime;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getWatchAddress() {
        return watchAddress;
    }

    public void setWatchAddress(String watchAddress) {
        this.watchAddress = watchAddress;
    }
}
