package com.blogspot.sslaia.gunews.repository;

import androidx.lifecycle.MutableLiveData;

import com.blogspot.sslaia.gunews.networking.NewsApi;
import com.blogspot.sslaia.gunews.networking.NewsService;
import com.blogspot.sslaia.gunews.webmodel.NewsItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsUrlRepository {
    private static NewsUrlRepository newsUrlRepository;
    private static NewsApi newsApi;

    public NewsUrlRepository() {
        newsApi = NewsService.cteateService(NewsApi.class);
    }

    public static NewsUrlRepository getInstance() {
        if (newsUrlRepository == null) {
            newsUrlRepository = new NewsUrlRepository();
        }
        return newsUrlRepository;
    }

    public MutableLiveData<NewsItem> getNewsUrl(String url) {
        final MutableLiveData<NewsItem> newsList = new MutableLiveData<>();

        newsApi.getNewsUrl(url).enqueue(new Callback<NewsItem>() {
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
