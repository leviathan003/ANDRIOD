package com.example.myapplication;

public class Specialists {
    private String name,experience,hospital,phone,fee,img_url;

    public Specialists(String name, String experience, String hospital, String phone, String fee, String img_url) {
        this.name = name;
        this.experience = experience;
        this.hospital = hospital;
        this.phone = phone;
        this.fee = fee;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "Specialists{" +
                "name='" + name + '\'' +
                ", experience='" + experience + '\'' +
                ", hospital='" + hospital + '\'' +
                ", phone='" + phone + '\'' +
                ", fee='" + fee + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
