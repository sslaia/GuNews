package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

public class NewsListViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String query;
    private String section;
    private String orderBy;
    private String showFields;
    private int page;
    private int pageSize;
    private String apiKey;

    public NewsListViewModelFactory(Application application, String query, String section, String orderBy, String showFields, int page, int pageSize, String apiKey) {
        this.application = application;
        this.query = query;
        this.section = section;
        this.orderBy = orderBy;
        this.showFields = showFields;
        this.page = page;
        this.pageSize = pageSize;
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsListViewModel(application, query, section, orderBy, showFields, page, pageSize, apiKey);
    }
}
