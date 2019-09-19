package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.sslaia.gunews.repository.SinglePageRepository;
import com.blogspot.sslaia.gunews.model.web.PageItem;

public class SinglePageViewModel extends ViewModel {

    private MutableLiveData<PageItem> newsLiveData;
    private SinglePageRepository singlePageRepository;
    private Application application;
    private String url;

    public SinglePageViewModel(Application application, String url) {
        this.application = application;
        this.url = url;
        init();
    }

    public void init() {
        if (newsLiveData != null) {
            return;
        }
        singlePageRepository = SinglePageRepository.getInstance();
        newsLiveData = singlePageRepository.getSinglePage(url);
    }

    public LiveData<PageItem> getSinglePageRepository() {
        return newsLiveData;
    }
}
