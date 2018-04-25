package com.ba.yo.innovativepasswordmanager.model;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Java-Ai-BOT on 4/11/2018.
 */

public interface ApiClient {

    @FormUrlEncoded
    @POST("accounts/delete/")
    Call<ResponseBody> deleteAccount(@Header("token") String token, @Field("accountID") String accountID);

    @GET("accounts/get/")
    Call<AccountModel> getAccount(@Header("token") String token, @Header("accountID") String accountID);

    /**
     * @param accountsJson - jsoned array of @AccountModel 's
     * @return response from server
     */
    @POST("users/changepass/")
    Call<ResponseBody> changeMasterPassowrd(@Header("token") String token,
                                            @Header("password") String newMasterPassword,
                                            @Body String accountsJson);

    @FormUrlEncoded
    @POST("accounts/transfer/")
    Call<ResponseBody> sendDataToApplet(@Header("token") String token,
                                        @Field("accountID") String accountID,
                                        @Field("appletID") String appletID);

    /**
     * Deletes all data from system. So this needs some verification such as @password in request
     *
     * @param token    - authorisation token
     * @param password - hashed master password
     * @return response from server
     */
    @POST("accounts/wipe/")
    Call<WipeResponse> wipeAllData(@Header("token") String token, @Header("password") String password);

    @POST("accounts/dump/")
    Call<List<AccountModel>> getDataDump(@Header("token") String token);

    /**
     * Get descriptions list and IDs of applets for currently authorised user
     */
    @GET("applets/descriptions/")
    Call<List<AppletDescriptionModel>> getAppletsDescriptions(@Header("token") String token);

    /**
     * Get descriptions list and IDs of accounts for currently authorised user
     */
    @GET("accounts/descriptions/")
    Call<List<EntityDescriptionModel>> getAccountsDescriptions(@Header("token") String token);


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

    @FormUrlEncoded
    @POST("users/signup/")
    Call<ResponseBody> signup(@Field("login") String login, @Field("password") String password);
}


