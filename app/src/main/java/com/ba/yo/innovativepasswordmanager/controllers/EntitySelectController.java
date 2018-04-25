package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.EntityDescriptionModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ford on 4/11/2018.
 */

public class EntitySelectController implements EntitySelectMVC.Controller {
    private EntitySelectMVC.View view;
    private ApiClient api;
    private final String TAG = "SELECT_CONTROLLER";

    public EntitySelectController(EntitySelectMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void getData() {
        Call<List<EntityDescriptionModel>> call = api.getAccountsDescriptions(CryptoCipher.getToken());
        call.enqueue(new Callback<List<EntityDescriptionModel>>() {
            @Override
            public void onResponse(Call<List<EntityDescriptionModel>> call, Response<List<EntityDescriptionModel>> response) {
                Log.d(TAG, response.toString());
                if (response.body() != null) {
                    view.clearList();
                    Log.d(TAG, response.body().size() + "");
                    List<EntityDescriptionModel> entities = response.body();
                    for (EntityDescriptionModel ent : entities) {
                        view.addEntity(ent.getDescription(), ent.getId());
                    }
                } else {
                    view.showNotification("Server error");
                }
            }

            @Override
            public void onFailure(Call<List<EntityDescriptionModel>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                view.showNotification(t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void deleteAccount(String accountId) {
        if (accountId == null) {
            Log.e(TAG, "Wrong id while delete");
            view.showNotification("wrong id!");
            return;
        }
        Call<ResponseBody> call = api.deleteAccount(CryptoCipher.getToken(), accountId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.toString());
                if (response.body() != null) {
                    ResponseBody resp = response.body();
                    view.clearList();
                    getData();
                    Log.d(TAG, resp + "");
                } else {
                    view.showNotification("Server error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                view.showNotification(t.getMessage());
                call.cancel();
            }
        });
    }
}
