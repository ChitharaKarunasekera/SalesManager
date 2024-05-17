package com.example.salesmanagerapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
    * RetrofitClient class to execute network requests
 **/
public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://www.axienta.lk/VantageCoreWebAPI/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}