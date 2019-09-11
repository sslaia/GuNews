package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.sslaia.gunews.networking.NewsRepository;
import com.blogspot.sslaia.gunews.webmodel.PageItem;

public class PageViewModel extends ViewModel {
    private MutableLiveData<PageItem> newsLiveData;
    private NewsRepository newsRepository;
    private Application application;
    private String url;

    public PageViewModel(Application application, String url) {
        this.application = application;
        this.url = url;
        init();
    }

    public void init() {
        if (newsLiveData != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
        newsLiveData = newsRepository.getPage(url);
    }

    public LiveData<PageItem> getNewsRepository() {
        return newsLiveData;
    }
}
