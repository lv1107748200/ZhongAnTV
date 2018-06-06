package com.hr.zhongantv.net.entry;

/**
 * Created by 吕 on 2018/3/28.
 * 会看数据
 * * id: 回看视频 id
 * name：会议名称
 * appointmentId:直播预约id
 * url:直播地址
 */

public class LookBackData extends BaseData {

    private String name;
    private String appointmentId;
    private String url;
    private String firstPicture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }
}
