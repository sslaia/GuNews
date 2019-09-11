package com.blogspot.sslaia.gunews.networking;

import com.blogspot.sslaia.gunews.webmodel.NewsItem;
import com.blogspot.sslaia.gunews.webmodel.PageItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsApi {

    @GET
    Call<NewsItem> getNews(@Url String url);

    @GET
    Call<PageItem> getPage(@Url String url);
}
