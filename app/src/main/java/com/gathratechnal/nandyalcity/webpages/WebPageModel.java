package com.gathratechnal.nandyalcity.webpages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arunmididoddy on 1/19/2018.
 */

public class WebPageModel {

    @SerializedName("Web_title")
    @Expose
    private String webTitle;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("aboutus")
    @Expose
    private String aboutus;
    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("contactus")
    @Expose
    private String contactus;
    @SerializedName("slidesdata")
    @Expose
    private Slidesdata slidesdata;
    @SerializedName("gallerydata")
    @Expose
    private Gallerydata gallerydata;

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getContactus() {
        return contactus;
    }

    public void setContactus(String contactus) {
        this.contactus = contactus;
    }

    public Slidesdata getSlidesdata() {
        return slidesdata;
    }

    public void setSlidesdata(Slidesdata slidesdata) {
        this.slidesdata = slidesdata;
    }

    public Gallerydata getGallerydata() {
        return gallerydata;
    }

    public void setGallerydata(Gallerydata gallerydata) {
        this.gallerydata = gallerydata;
    }


}
