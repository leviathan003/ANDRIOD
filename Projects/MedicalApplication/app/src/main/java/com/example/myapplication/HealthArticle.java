package com.example.myapplication;

public class HealthArticle {
    private String thumbnail,url;

    public HealthArticle(String thumbnail, String url) {
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }
}
