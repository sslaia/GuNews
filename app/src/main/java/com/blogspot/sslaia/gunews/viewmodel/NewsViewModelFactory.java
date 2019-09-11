package com.blogspot.sslaia.gunews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String url;

    public NewsViewModelFactory(Application application, String url) {
        this.application = application;
        this.url = url;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(application, url);
    }
}
