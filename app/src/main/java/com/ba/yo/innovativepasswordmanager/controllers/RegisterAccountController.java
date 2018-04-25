package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.RegisterAccountMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterAccountController implements RegisterAccountMVC.Controller {

    private RegisterAccountMVC.View view;
    private ApiClient api;
    private final String TAG = "REGISTRATION";

    //TODO: go back to login via view.returnToLoginScreen()

    public RegisterAccountController(RegisterAccountMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void registerAccount() {
        final String login = view.getLogin();
        final String password = view.getPassword();
        if (login == null) {
            view.showNotification("Login can not be empty");
            return;
        }
        if (password == null) {
            view.showNotification("Password can not be empty");
            return;
        }
        Call<ResponseBody> call = api.signup(login, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    view.showNotification("Successfully registered");
                    try {
                        view.returnToLoginScreen(CryptoCipher.hash256(login),
                                CryptoCipher.hash256(password));
                    } catch (NoSuchAlgorithmException e) {
                        Log.e(TAG, "onResponse: " + e);
                        view.showNotification("Crypto error occurred");
                    }
                } else {
                    view.showNotification("Error occurred: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showNotification("Error occurred!\n" + t.getLocalizedMessage());
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

}
