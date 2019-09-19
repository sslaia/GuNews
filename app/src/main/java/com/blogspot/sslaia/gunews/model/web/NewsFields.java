package com.blogspot.sslaia.gunews.model.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsFields {

    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("shortUrl")
    @Expose
    private String shortUrl;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
