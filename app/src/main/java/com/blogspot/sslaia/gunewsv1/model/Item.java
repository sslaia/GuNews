package com.blogspot.sslaia.gunewsv1.model;

public class Item {

    private Response response;

    public Item(Response response) {
        this.response = response;
    }

    public Response getNewsResponse() {
        return response;
    }

    public void setNewsResponse(Response response) {
        this.response = response;
    }

}
