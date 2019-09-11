package com.blogspot.sslaia.gunews.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://content.guardianapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
