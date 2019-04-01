package com.gathratechnal.nandyalcity.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arunmididoddy on 1/6/2018.
 */

public class VipDataModel {
    @SerializedName("directory_Vip_catid")
    @Expose
    private String directoryVipCatid;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("cat_app_icon")
    @Expose
    private String catAppIcon;
    @SerializedName("vip_directory_name")
    @Expose
    private String vipDirectoryName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("directory_photo")
    @Expose
    private String directoryPhoto;
    @SerializedName("date")
    @Expose
    private String date;

    public String getDirectoryVipCatid() {
        return directoryVipCatid;
    }

    public void setDirectoryVipCatid(String directoryVipCatid) {
        this.directoryVipCatid = directoryVipCatid;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatAppIcon() {
        return catAppIcon;
    }

    public void setCatAppIcon(String catAppIcon) {
        this.catAppIcon = catAppIcon;
    }

    public String getVipDirectoryName() {
        return vipDirectoryName;
    }

    public void setVipDirectoryName(String vipDirectoryName) {
        this.vipDirectoryName = vipDirectoryName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDirectoryPhoto() {
        return directoryPhoto;
    }

    public void setDirectoryPhoto(String directoryPhoto) {
        this.directoryPhoto = directoryPhoto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
