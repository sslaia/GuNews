package com.blogspot.sslaia.gunews.networking;

import com.blogspot.sslaia.gunews.webmodel.NewsItem;
import com.blogspot.sslaia.gunews.webmodel.PageItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NewsApi {

    @GET
    Call<NewsItem> getNewsUrl(@Url String url);

    @GET
    Call<PageItem> getSinglePage(@Url String url);

    @GET("search")
    Call<NewsItem> getNewsList(@Query("q") String query,
                               @Query("section") String section,
                               @Query("order-by") String orderBy,
                               @Query("show-fields") String showFields,
                               @Query("page-size") String pageSize,
                               @Query("api-key") String apiKey);

}
