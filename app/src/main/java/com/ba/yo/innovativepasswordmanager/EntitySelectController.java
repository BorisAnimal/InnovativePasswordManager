package com.ba.yo.innovativepasswordmanager;

import android.util.Log;

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
        Call<List<EntityAccountModel>> call = api.getEntityAccounts();
        call.enqueue(new Callback<List<EntityAccountModel>>() {
            @Override
            public void onResponse(Call<List<EntityAccountModel>> call, Response<List<EntityAccountModel>> response) {
                Log.d(TAG, response.toString());
                Log.d(TAG, response.body().size() + "");
                if (response.body() != null) {
                    List<EntityAccountModel> entities = response.body();
                    for (EntityAccountModel ent : entities) {
                        view.addEntity(ent.getDescription(), ent.getId());
                    }
                } else {
                    view.showNotification("Server unavailable");
                }
            }

            @Override
            public void onFailure(Call<List<EntityAccountModel>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                view.showNotification(t.getMessage());
                call.cancel();
            }
        });
    }
}
