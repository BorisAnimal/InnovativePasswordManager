package com.ba.yo.innovativepasswordmanager.model;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Java-Ai-BOT on 4/11/2018.
 */

public class RetrofitService {

    private static final String BASE_URL = "http://10.90.137.88:5384/";

    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit;
    }
}
