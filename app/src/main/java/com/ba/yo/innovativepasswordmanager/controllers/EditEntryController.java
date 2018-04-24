package com.ba.yo.innovativepasswordmanager.controllers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.EntryModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ba.yo.innovativepasswordmanager.model.CryptoCipher.encrypt;
import static com.ba.yo.innovativepasswordmanager.model.CryptoCipher.getToken;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class EditEntryController implements EditEntryMVC.Controller {
    private EditEntryMVC.View view;
    @Nullable
    private String modelId;
    private ApiClient api;
    private EntryModel model;
    /**
     * When we create entry
     */
    public EditEntryController(EditEntryMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    /**
     * When we edit entry
     */
    public EditEntryController(EditEntryMVC.View view, String id) {
        this.view = view;
        this.modelId = id;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void commitEntry(String login, String pass, String description) {
        //We post new model instance
        if (model == null) {
            model = new EntryModel();
        }
        if (login != null)
            model.setLogin(login);
        else {
            view.showNotification("Enter login!");
            return;
        }
        if (pass != null)
            model.setPassword(pass);
        else {
            view.showNotification("Enter password!");
            return;
        }
        model.setDescription(description);
        Call<ResponseBody> call = api.postAccount(getToken(), encrypt(model.getLogin()),
                encrypt(model.getPassword()), model.getDescription());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    view.showNotification("Successfully sent!");
                } else
                    view.showNotification("Error occurred: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showNotification("Error occurred!\n" + t.getLocalizedMessage());
                Log.e("EditEntity", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void generateRandomPassword() {
        final int randomStrLength = 15;
        final char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi" +
                "jklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
        String randomPassword = RandomStringUtils.random(randomStrLength, 0,
                possibleCharacters.length - 1, false, false, possibleCharacters,
                new SecureRandom());
        view.setPassword(randomPassword);
        view.showNotification("Random password generated");
    }
}
