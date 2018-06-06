package com.hr.zhongantv.net.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 吕 on 2017/8/1.
 * "postId": 188,
 "postViewId": 107,
 "authorId": 10,
 "groupId": null,
 "postFirstPicture": "http://oc5bhgi99.bkt.clouddn.com/test/1.png",
 "postSummary": "添加的文本内容2222",
 "postPictureList": [
 "http://oc5bhgi99.bkt.clouddn.com/test/1.png",
 "http://oc5bhgi99.bkt.clouddn.com/test/2.png",
 "http://oc5bhgi99.bkt.clouddn.com/test/3.png",
 "http://oc5bhgi99.bkt.clouddn.com/test/4.png"
 ]
 */

public class AddPostData implements Parcelable {

    private String postId;
    private String postViewId;
    private String authorId;//postId的作者
    private String ownerId;//这个是postViewId的作者
    private String groupId;
    private String postFirstPicture;
    private String postSummary;
    private String postViewDeleteFlg;
    private String isValid;//1代表上线 0下线
    private String postTypeName;//帖子模板名称
    private List<String> postPictureList;

    public String getPostTypeName() {
        return postTypeName;
    }

    public void setPostTypeName(String postTypeName) {
        this.postTypeName = postTypeName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPostViewDeleteFlg() {
        return postViewDeleteFlg;
    }

    public void setPostViewDeleteFlg(String postViewDeleteFlg) {
        this.postViewDeleteFlg = postViewDeleteFlg;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostViewId() {
        return postViewId;
    }

    public void setPostViewId(String postViewId) {
        this.postViewId = postViewId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPostFirstPicture() {
        return postFirstPicture;
    }

    public void setPostFirstPicture(String postFirstPicture) {
        this.postFirstPicture = postFirstPicture;
    }

    public String getPostSummary() {
        return postSummary;
    }

    public void setPostSummary(String postSummary) {
        this.postSummary = postSummary;
    }

    public List<String> getPostPictureList() {
        return postPictureList;
    }

    public void setPostPictureList(List<String> postPictureList) {
        this.postPictureList = postPictureList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postId);
        dest.writeString(this.postViewId);
        dest.writeString(this.authorId);
        dest.writeString(this.ownerId);
        dest.writeString(this.groupId);
        dest.writeString(this.postFirstPicture);
        dest.writeString(this.postSummary);
        dest.writeString(this.postViewDeleteFlg);
        dest.writeString(this.isValid);
        dest.writeString(this.postTypeName);
        dest.writeStringList(this.postPictureList);
    }

    public AddPostData() {
    }

    protected AddPostData(Parcel in) {
        this.postId = in.readString();
        this.postViewId = in.readString();
        this.authorId = in.readString();
        this.ownerId = in.readString();
        this.groupId = in.readString();
        this.postFirstPicture = in.readString();
        this.postSummary = in.readString();
        this.postViewDeleteFlg = in.readString();
        this.isValid = in.readString();
        this.postTypeName = in.readString();
        this.postPictureList = in.createStringArrayList();
    }

    public static final Creator<AddPostData> CREATOR = new Creator<AddPostData>() {
        @Override
        public AddPostData createFromParcel(Parcel source) {
            return new AddPostData(source);
        }

        @Override
        public AddPostData[] newArray(int size) {
            return new AddPostData[size];
        }
    };
}
