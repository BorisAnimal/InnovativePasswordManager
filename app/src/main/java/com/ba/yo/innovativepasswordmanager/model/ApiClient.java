package com.ba.yo.innovativepasswordmanager.model;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Java-Ai-BOT on 4/11/2018.
 */

public interface ApiClient {

    /**
     * Get descriptions list and IDs for currently authorised user
     */
    @GET("accounts/descriptions/")
    Call<List<EntitySelectModel>> getEntityAccounts(@Header("token") String token);


    /**
     * Post new account
     */
    @FormUrlEncoded
    @POST("accounts/")
    Call<ResponseBody> postAccount(@Header("token") String token,
                               @Field("login") String login,
                               @Field("password") String password,
                               @Field("description") String description);


    /**
     * Update existing account
     */
    @FormUrlEncoded
    @POST("accounts/")
    Call<ResponseBody> postAccount(@Header("token") String token,
                                   @Field("login") String login,
                                   @Field("password") String password,
                                   @Field("description") String description,
                                   @Field("id") String id);

    /**
     * Check if user exists in our system
     */
    @POST("users/check/")
    Call<LoginResponseModel> checkUser(@Body RequestBody params);
            //@Header("login") String login, @Header("pass") String pass);
}


