package com.blogspot.sslaia.gunews.webmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageFields {

    @SerializedName("headline")
    @Expose
    private String headline;

    @SerializedName("byline")
    @Expose
    private String byline;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("shortUrl")
    @Expose
    private String shortUrl;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
