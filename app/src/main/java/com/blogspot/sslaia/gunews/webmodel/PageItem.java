package com.blogspot.sslaia.gunews.webmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageItem {

    @SerializedName("response")
    @Expose
    private PageResponse response;

    public PageResponse getPageResponse() {
        return response;
    }

    public void setPageResponse(PageResponse response) {
        this.response = response;
    }

}
