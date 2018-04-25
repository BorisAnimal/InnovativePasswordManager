package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.AppletSelectMVC;
import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.AppletDescriptionModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Java-Ai-BOT on 4/20/2018.
 */

public class AppletSelectController implements AppletSelectMVC.Controller {

    private AppletSelectMVC.View view;
    private ApiClient api;
    private final String TAG = "SELECT_APPLET";

    //TODO: call view.goToEntitySelectActivity() on successful operation

    public AppletSelectController(AppletSelectMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void getData() {
        Call<List<AppletDescriptionModel>> call = api.getAppletsDescriptions(CryptoCipher.getToken());
        call.enqueue(new Callback<List<AppletDescriptionModel>>() {
            @Override
            public void onResponse(Call<List<AppletDescriptionModel>> call, Response<List<AppletDescriptionModel>> response) {
                Log.d(TAG, response.toString());
                if (response.body() != null) {
                    Log.d(TAG, response.body().size() + "");
                    List<AppletDescriptionModel> applets = response.body();
                    for (AppletDescriptionModel applet : applets) {
                        view.addApplet(applet.getDescription(), applet.getId());
                    }
                } else {
                    view.showNotification("Server unavailable");
                }
            }

            @Override
            public void onFailure(Call<List<AppletDescriptionModel>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                view.showNotification(t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void sendDataToApplet(String appletId, String accountId) {
        if (!(appletId != null && appletId.length() > 0 && accountId != null && accountId.length() > 0)) {
            view.showNotification("Wrong parameter");
            return;
        }
        Call<ResponseBody> call = api.sendDataToApplet(CryptoCipher.getToken(), accountId, appletId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    view.showNotification("Successfully sent.");
                    view.onSuccessTransfer();
                } else {
                    view.showNotification("Error occurred: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showNotification("Error occurred!\n" + t.getLocalizedMessage());
                Log.e("AppletSelect", t.getLocalizedMessage());
            }
        });
    }
}
