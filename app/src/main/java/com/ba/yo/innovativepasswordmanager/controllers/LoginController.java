package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.LoginMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.LoginResponseModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import java.security.NoSuchAlgorithmException;
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
    private final String TAG = "LoginController";
    private LoginMVC.View view;
    private ApiClient api;

    public LoginController(LoginMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void check(String login, String password) {
        if (login == null || Objects.equals(login, "") || password == null || Objects.equals(password, "")) {
            view.showNotification("Login and Password fields can not be empty.");
            return;
        }
        CryptoCipher.storeMP(password);
        try {
            Call<LoginResponseModel> call = api.checkUser(RequestBody.create(
                    MediaType.parse("application/json"), toJson(CryptoCipher.hash256(login),
                            CryptoCipher.hash256(password))));
            call.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    if (response.body() != null) {
                        LoginResponseModel resp = response.body();
                        if (resp.getErrorMessage() == null || resp.getErrorMessage().contains("null")) {
                            CryptoCipher.storeToken(resp.getSessionToken());
                            view.showNotification("All good");
                            view.goToEntitySelectActivity();
                        } else {
                            view.showNotification("Error accurend: " + resp.getErrorMessage());
                        }
                    } else {
                        view.showNotification("Server error");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    view.showNotification(t.getMessage());
                    call.cancel();
                }
            });
        } catch (NoSuchAlgorithmException ex) {
            Log.e(TAG, "Crypto exception: " + ex.getLocalizedMessage());
            view.showNotification("Crypto exception");
        }
    }

    private String toJson(String login, String password) {
        return String.format("{\n" +
                "        \"username\": \"" + login + "\",\n" +
                "        \"password\": \"" + password + "\"\n" +
                "    }");
    }
}
