package com.blogspot.sslaia.gunews.repository;

import androidx.lifecycle.MutableLiveData;

import com.blogspot.sslaia.gunews.api.NewsApi;
import com.blogspot.sslaia.gunews.api.NewsApiFactory;
import com.blogspot.sslaia.gunews.model.web.NewsItem;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListRepository {
    private static NewsListRepository newsListRepository;
    private static NewsApi newsApi;

    public NewsListRepository() {
        newsApi = NewsApiFactory.cteateService(NewsApi.class);
    }

    public static NewsListRepository getInstance() {
        if (newsListRepository == null) {
            newsListRepository = new NewsListRepository();
        }
        return newsListRepository;
    }

    public MutableLiveData<NewsItem> getNewsList(String query, String section, String orderBy, String showFields, int page, int pageSize, String apiKey) {
        final MutableLiveData<NewsItem> newsList = new MutableLiveData<>();

        newsApi.getNewsList(query, section,orderBy, showFields, page, pageSize, apiKey).enqueue(new Callback<NewsItem>() {
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
}
