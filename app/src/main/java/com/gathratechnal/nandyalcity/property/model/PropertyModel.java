package com.gathratechnal.nandyalcity.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arunmididoddy on 1/6/2018.
 */

public class PropertyModel {

    @SerializedName("user_nccode")
    @Expose
    private String userNccode;
    @SerializedName("propearty_id")
    @Expose
    private String propeartyId;
    @SerializedName("propearty_title")
    @Expose
    private String propeartyTitle;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("img1")
    @Expose
    private String img1;
    @SerializedName("img2")
    @Expose
    private String img2;
    @SerializedName("img3")
    @Expose
    private String img3;
    @SerializedName("img4")
    @Expose
    private String img4;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("pay")
    @Expose
    private String pay;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUserNccode() {
        return userNccode;
    }

    public void setUserNccode(String userNccode) {
        this.userNccode = userNccode;
    }

    public String getPropeartyId() {
        return propeartyId;
    }

    public void setPropeartyId(String propeartyId) {
        this.propeartyId = propeartyId;
    }

    public String getPropeartyTitle() {
        return propeartyTitle;
    }

    public void setPropeartyTitle(String propeartyTitle) {
        this.propeartyTitle = propeartyTitle;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
