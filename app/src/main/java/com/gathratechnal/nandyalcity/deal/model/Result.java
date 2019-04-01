package com.gathratechnal.nandyalcity.deal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("deal_trnid")
    @Expose
    private String dealTrnid;
    @SerializedName("deal_title")
    @Expose
    private String dealTitle;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("web_image")
    @Expose
    private String webImage;
    @SerializedName("app_image")
    @Expose
    private String appImage;
    @SerializedName("exp_date")
    @Expose
    private String expDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealTrnid() {
        return dealTrnid;
    }

    public void setDealTrnid(String dealTrnid) {
        this.dealTrnid = dealTrnid;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebImage() {
        return webImage;
    }

    public void setWebImage(String webImage) {
        this.webImage = webImage;
    }

    public String getAppImage() {
        return appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}
