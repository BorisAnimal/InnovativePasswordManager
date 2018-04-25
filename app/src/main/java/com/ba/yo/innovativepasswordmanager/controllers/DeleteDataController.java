package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.WipeDataMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;
import com.ba.yo.innovativepasswordmanager.model.WipeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteDataController implements WipeDataMVC.Controller {

    private WipeDataMVC.View view;
    private ApiClient api;
    private final String TAG = "WIPE_CONTROLLER";

    public DeleteDataController(WipeDataMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void fullWipe() {
        if (!view.isCheckboxChecked()) {
            view.showNotification("Provide full delete acceptances");
            return;
        }
        String password = view.getMasterPassword();
        if (password == null) {
            view.showNotification("Enter password!");
            return;
        }
        if (CryptoCipher.checkMP(password)) {
            Call<WipeResponse> call = api.wipeAllData(CryptoCipher.getToken(), password);
            call.enqueue(new Callback<WipeResponse>() {
                @Override
                public void onResponse(Call<WipeResponse> call, Response<WipeResponse> response) {
                    Log.d(TAG, response.toString());
                    if (response.body() != null) {
                        Log.d(TAG, response.body() + "");
                        WipeResponse resp = response.body();
                        if (resp != null) {
                            view.showNotification(String.format("Removed "
                                    + resp.getAccounts_deleted() + " accounts."));
                        }
                    } else {
                        view.showNotification("Server unavailable");
                    }
                }

                @Override
                public void onFailure(Call<WipeResponse> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                    view.showNotification(t.getMessage());
                    call.cancel();
                }
            });
        } else {
            view.showNotification("Password is incorrect!");
        }
    }
}
