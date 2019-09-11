package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.sslaia.gunews.networking.NewsRepository;
import com.blogspot.sslaia.gunews.webmodel.NewsItem;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<NewsItem> newsLiveData;
    private NewsRepository newsRepository;
    private Application application;
    private String url;

    public NewsViewModel(Application application, String url) {
        this.application = application;
        this.url = url;
        init();
    }

    public void init() {
        if (newsLiveData != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
        newsLiveData = newsRepository.getNews(url);
    }

    public LiveData<NewsItem> getNewsRepository() {
        return newsLiveData;
    }
}
