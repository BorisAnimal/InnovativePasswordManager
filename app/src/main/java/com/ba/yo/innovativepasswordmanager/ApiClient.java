package com.ba.yo.innovativepasswordmanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Java-Ai-BOT on 4/11/2018.
 */

public interface ApiClient {
    @GET("accounts/api/v1.0/")
    Call<List<EntityAccountModel>> getEntityAccounts();
}
