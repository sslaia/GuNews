package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.sslaia.gunews.repository.NewsUrlRepository;
import com.blogspot.sslaia.gunews.webmodel.NewsItem;

public class NewsUrlViewModel extends ViewModel {

    private MutableLiveData<NewsItem> newsLiveData;
    private NewsUrlRepository newsUrlRepository;
    private Application application;
    private String url;

    public NewsUrlViewModel(Application application, String url) {
        this.application = application;
        this.url = url;
        init();
    }

    public void init() {
        if (newsLiveData != null) {
            return;
        }
        newsUrlRepository = NewsUrlRepository.getInstance();
        newsLiveData = newsUrlRepository.getNewsUrl(url);
    }

    public LiveData<NewsItem> getNewsUrlRepository() {
        return newsLiveData;
    }
}
