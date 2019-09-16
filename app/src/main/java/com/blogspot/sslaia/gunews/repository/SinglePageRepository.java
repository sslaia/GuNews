package com.blogspot.sslaia.gunews.repository;

import androidx.lifecycle.MutableLiveData;

import com.blogspot.sslaia.gunews.networking.NewsApi;
import com.blogspot.sslaia.gunews.networking.NewsService;
import com.blogspot.sslaia.gunews.webmodel.PageItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePageRepository {
    private static SinglePageRepository singlePageRepository;
    private static NewsApi newsApi;

    public SinglePageRepository() {
        newsApi = NewsService.cteateService(NewsApi.class);
    }

    public static SinglePageRepository getInstance() {
        if (singlePageRepository == null) {
            singlePageRepository = new SinglePageRepository();
        }
        return singlePageRepository;
    }

    public MutableLiveData<PageItem> getSinglePage(String url) {
        final MutableLiveData<PageItem> pageList = new MutableLiveData<>();

        newsApi.getSinglePage(url).enqueue(new Callback<PageItem>() {
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
