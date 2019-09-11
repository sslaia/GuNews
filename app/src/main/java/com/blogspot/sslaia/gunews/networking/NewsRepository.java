package com.blogspot.sslaia.gunews.networking;

import androidx.lifecycle.MutableLiveData;

import com.blogspot.sslaia.gunews.webmodel.NewsItem;
import com.blogspot.sslaia.gunews.webmodel.PageItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private static NewsRepository newsRepository;
    private static NewsApi newsApi;

    public NewsRepository() {
        newsApi = NewsService.cteateService(NewsApi.class);
    }

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<NewsItem> getNews(String url) {
        final MutableLiveData<NewsItem> newsList = new MutableLiveData<>();

        newsApi.getNews(url).enqueue(new Callback<NewsItem>() {
            @Override
            public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                if (response.isSuccessful()) {
                    newsList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsItem> call, Throwable t) {
                newsList.setValue(null);
            }
        });
        return newsList;
    }

    public MutableLiveData<PageItem> getPage(String url) {
        final MutableLiveData<PageItem> pageList = new MutableLiveData<>();

        newsApi.getPage(url).enqueue(new Callback<PageItem>() {
            @Override
            public void onResponse(Call<PageItem> call, Response<PageItem> response) {
                if (response.isSuccessful()) {
                    pageList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PageItem> call, Throwable t) {
                pageList.setValue(null);
            }
        });
        return pageList;
    }
}
