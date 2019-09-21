package com.blogspot.sslaia.gunews.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.blogspot.sslaia.gunews.datasource.NewsDataSourceFactory;
import com.blogspot.sslaia.gunews.model.web.NewsResult;

public class NewsPagedViewModel extends ViewModel {

    LiveData<PagedList<NewsResult>> newsPagedList;
    LiveData<PageKeyedDataSource<Integer, NewsResult>> liveDataSource;

    public NewsPagedViewModel() {

        NewsDataSourceFactory newsDataSourceFactory = new NewsDataSourceFactory();
        liveDataSource = newsDataSourceFactory.getNewsLiveDataSource();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20)
                        .build();

        newsPagedList =
                (new LivePagedListBuilder(newsDataSourceFactory, pagedListConfig))
                .build();
    }
}
