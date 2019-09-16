package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewsListViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String query;
    private String section;
    private String showFields;
    private String pageSize;
    private String apiKey;

    public NewsListViewModelFactory(Application application, String query, String section, String showFields, String pageSize, String apiKey) {
        this.application = application;
        this.query = query;
        this.section = section;
        this.showFields = showFields;
        this.pageSize = pageSize;
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsListViewModel(application, query, section, showFields, pageSize, apiKey);
    }
}
