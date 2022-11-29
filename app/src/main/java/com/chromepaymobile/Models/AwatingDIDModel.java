package com.chromepaymobile.Models;

public class AwatingDIDModel {

    String id;
    String name;
    String photo;
    String phone;

    public AwatingDIDModel() {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.phone = phone;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
