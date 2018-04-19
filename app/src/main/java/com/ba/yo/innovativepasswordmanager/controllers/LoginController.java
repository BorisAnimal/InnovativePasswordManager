package com.ba.yo.innovativepasswordmanager.controllers;

import android.app.DownloadManager;
import android.util.Log;

import com.ba.yo.innovativepasswordmanager.model.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.LoginMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.LoginResponseModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class LoginController implements LoginMVC.Controller {
    private LoginMVC.View view;
    private ApiClient api;
    private final String TAG = "LoginController";

    public LoginController(LoginMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void check(String login, String password) {
        if (Objects.equals(login, "") || Objects.equals(password, "")) {
            view.showNotification("Login and Password fields can not be empty.");
            return;
        }
        CryptoCipher.storeMP(password);
        Call<LoginResponseModel> call = api.checkUser(RequestBody.create(
                MediaType.parse("application/json"), toJson(CryptoCipher.hash256(login),
                        CryptoCipher.hash256(password))));
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                Log.d(TAG, response.toString());
                if (response.body() != null) {
                    LoginResponseModel resp = response.body();
                    Log.d(TAG, resp + "");
                    if (resp.getErrorMessage() == null || resp.getErrorMessage().contains("null")) {
                        Log.d(TAG, response.toString());
                        CryptoCipher.storeToken(resp.getSessionToken());
                        view.showNotification("All good");
                        view.makeTransitionToEntitySelect();
                    } else {
                        view.showNotification("Error accurend: " + resp.getErrorMessage());
                    }
                } else {
                    view.showNotification("Server unavailable");
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                view.showNotification(t.getMessage());
                call.cancel();
            }
        });
    }

    private String toJson(String login, String password) {
        Log.d(TAG, login + " " + password);
        return String.format("{\n" +
                "        \"username\": \"" + login + "\",\n" +
                "        \"password\": \"" + password + "\"\n" +
                "    }");
    }
}
