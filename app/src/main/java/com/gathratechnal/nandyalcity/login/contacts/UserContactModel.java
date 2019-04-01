package com.gathratechnal.nandyalcity.login.contacts;

public class UserContactModel {

    private String id;
    private String name;
    private String phone;
    private String normalizedPhone;
    private int isMobileNumber;
    private int isStaredContact;
    private String photo;

    /**Getters and setters
     * */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNormalizedPhone() {
        return normalizedPhone;
    }

    public void setNormalizedPhone(String normalizedPhone) {
        this.normalizedPhone = normalizedPhone;
    }

    public int getIsStaredContact() {
        return isStaredContact;
    }

    public void setIsStaredContact(int isStaredContact) {
        this.isStaredContact = isStaredContact;
    }

    public String getPhoto() {
        return photo == null ? "" : photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIsMobileNumber() {
        return isMobileNumber;
    }

    public void setIsMobileNumber(int isMobileNumber) {
        this.isMobileNumber = isMobileNumber;
    }
}
