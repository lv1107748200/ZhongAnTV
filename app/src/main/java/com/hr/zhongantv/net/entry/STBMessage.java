package com.hr.zhongantv.net.entry;

import java.util.List;

/**
 * Created by 吕 on 2018/3/28.
 * 查询该机顶盒的相关信息
 * * id: 机顶盒 id
 * serialNumber:机顶盒序列号
 * enterpriseName：企业名称
 * linkman:企业 联系人
 * mobile：联系电话
 * organizationId:主管单位id
 * organizationName:主管单位名称
 */

public class STBMessage extends BaseData{

    private String serialNumber;
    private String enterpriseName;

    private String organizationId;
    private String organizationName;

    private List<LinkManRes> linkManResList;


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<LinkManRes> getLinkManResList() {
        return linkManResList;
    }

    public void setLinkManResList(List<LinkManRes> linkManResList) {
        this.linkManResList = linkManResList;
    }
}
