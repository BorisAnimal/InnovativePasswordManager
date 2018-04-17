package com.ba.yo.innovativepasswordmanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Java-Ai-BOT on 4/11/2018.
 */

public interface ApiClient {
    @GET("accounts/v1.0/")
    Call<List<EntitySelectModel>> getEntityAccounts();

    @POST("accounts/v1.0/")
    Call<POST> postAccount(@Header("token") String token, @Header("login") String login,
                           @Header("pass") String password, @Header("descr") String description);


    @GET("/CheckUser")
    Call<LoginResponseModel> checkUser(@Query("login") String login, @Query("pass") String pass);
//    Call<LoginResponseModel> checkUser(@Header("login") String login, @Header("pass") String pass);
}


