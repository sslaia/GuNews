package com.blogspot.sslaia.gunews.model.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsItem {

    @SerializedName("response")
    @Expose
    private NewsResponse response;

    public NewsResponse getNewsResponse() {
        return response;
    }

    public void setResponse(NewsResponse response) {
        this.response = response;
    }

}