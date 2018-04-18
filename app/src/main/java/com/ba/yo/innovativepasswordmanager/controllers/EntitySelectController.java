package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.EntitySelectModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import java.util.List;

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
        Call<List<EntitySelectModel>> call = api.getEntityAccounts();
        call.enqueue(new Callback<List<EntitySelectModel>>() {
            @Override
            public void onResponse(Call<List<EntitySelectModel>> call, Response<List<EntitySelectModel>> response) {
                Log.d(TAG, response.toString());
                if (response.body() != null) {
                    Log.d(TAG, response.body().size() + "");
                    List<EntitySelectModel> entities = response.body();
                    for (EntitySelectModel ent : entities) {
                        view.addEntity(ent.getDescription(), ent.getId());
                    }
                } else {
                    view.showNotification("Server unavailable");
                }
            }

            @Override
            public void onFailure(Call<List<EntitySelectModel>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                view.showNotification(t.getMessage());
                call.cancel();
            }
        });
    }

}
