package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.sslaia.gunews.repository.NewsListRepository;
import com.blogspot.sslaia.gunews.model.web.NewsItem;

public class NewsListViewModel extends ViewModel {

    private MutableLiveData<NewsItem> newsLiveData;
    private NewsListRepository newsListRepository;
    private Application application;
    private String query;
    private String section;
    private String orderBy;
    private String showFields;
    private String pageSize;
    private String apiKey;

    public NewsListViewModel(Application application, String query, String section, String orderBy, String showFields, String pageSize, String apiKey) {
        this.application = application;
        this.query = query;
        this.section = section;
        this.orderBy = orderBy;
        this.showFields = showFields;
        this.pageSize = pageSize;
        this.apiKey = apiKey;
        init();
    }

    public void init() {
        if (newsLiveData != null) {
            return;
        }
        newsListRepository = NewsListRepository.getInstance();
        newsLiveData = newsListRepository.getNewsList(query, section, orderBy, showFields, pageSize, apiKey);
    }

    public LiveData<NewsItem> getNewsListRepository() {
        return newsLiveData;
    }
}
