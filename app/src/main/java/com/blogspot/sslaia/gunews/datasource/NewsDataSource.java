package com.blogspot.sslaia.gunews.datasource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.blogspot.sslaia.gunews.api.NewsApi;
import com.blogspot.sslaia.gunews.api.NewsApiFactory;
import com.blogspot.sslaia.gunews.model.web.NewsItem;
import com.blogspot.sslaia.gunews.model.web.NewsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Integer, NewsResult> {

    private static final String QUERY = "";
    private static final String SECTION = "";
    private static final String ORDER_BY = "";
    private static final String SHOW_FIELDS = "";
    private static final int PAGE = 1;
    public static final int PAGE_SIZE = 15;
    private static final String API_KEY = "";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, NewsResult> callback) {
        NewsApiFactory.cteateService(NewsApi.class)
                .getNewsList(QUERY, SECTION, ORDER_BY, SHOW_FIELDS, 1, PAGE_SIZE, API_KEY)
                .enqueue(new Callback<NewsItem>() {
                    @Override
                    public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getNewsResponse().getNewsResults(), null, PAGE + 1);
                        }

                    }

                    @Override
                    public void onFailure(Call<NewsItem> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, NewsResult> callback) {
        NewsApiFactory.cteateService(NewsApi.class)
                .getNewsList(QUERY, SECTION, ORDER_BY, SHOW_FIELDS, PAGE, PAGE_SIZE, API_KEY)
                .enqueue(new Callback<NewsItem>() {
                    @Override
                    public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {

                        if (response.body() != null) {
                            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().getNewsResponse().getNewsResults(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsItem> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, NewsResult> callback) {

        NewsApiFactory.cteateService(NewsApi.class)
                .getNewsList(QUERY, SECTION, ORDER_BY, SHOW_FIELDS, PAGE, PAGE_SIZE, API_KEY)
                .enqueue(new Callback<NewsItem>() {
                    @Override
                    public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                        if (response.body() != null) {
                            Integer nextKey = (params.key == response.body().getNewsResponse().getPages()) ? null : params.key + 1;
                            callback.onResult(response.body().getNewsResponse().getNewsResults(), nextKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsItem> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
