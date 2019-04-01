package com.gathratechnal.nandyalcity.webpages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arunmididoddy on 1/19/2018.
 */

public class Slidesdata {
    @SerializedName("slide1")
    @Expose
    private String slide1;
    @SerializedName("slide2")
    @Expose
    private String slide2;
    @SerializedName("slide3")
    @Expose
    private String slide3;
    @SerializedName("slide4")
    @Expose
    private String slide4;

    public String getSlide1() {
        return slide1;
    }

    public void setSlide1(String slide1) {
        this.slide1 = slide1;
    }

    public String getSlide2() {
        return slide2;
    }

    public void setSlide2(String slide2) {
        this.slide2 = slide2;
    }

    public String getSlide3() {
        return slide3;
    }

    public void setSlide3(String slide3) {
        this.slide3 = slide3;
    }

    public String getSlide4() {
        return slide4;
    }

    public void setSlide4(String slide4) {
        this.slide4 = slide4;
    }

}

