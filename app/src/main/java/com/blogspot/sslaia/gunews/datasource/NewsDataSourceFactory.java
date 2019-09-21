package com.blogspot.sslaia.gunews.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.blogspot.sslaia.gunews.model.web.NewsResult;

public class NewsDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, NewsResult>> newsLiveDataSource = new MutableLiveData<>();
    private NewsDataSource newsDataSource;

    @NonNull
    @Override
    public DataSource<Integer, NewsResult> create() {
        newsDataSource = new NewsDataSource();
        newsLiveDataSource.postValue(newsDataSource);
        return newsDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, NewsResult>> getNewsLiveDataSource() {
        return newsLiveDataSource;
    }
}
