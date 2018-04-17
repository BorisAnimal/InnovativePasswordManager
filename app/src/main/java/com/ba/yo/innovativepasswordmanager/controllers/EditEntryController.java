package com.ba.yo.innovativepasswordmanager.controllers;

import android.support.annotation.Nullable;

import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.EntryModel;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

import static com.ba.yo.innovativepasswordmanager.model.CryptoCipher.*;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class EditEntryController implements EditEntryMVC.Controller {
    private EditEntryMVC.View view;
    @Nullable
    private EntryModel model;
    private ApiClient api;

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
    public EditEntryController(EditEntryMVC.View view, EntryModel model) {
        this.view = view;
        this.model = model;
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

        Call<POST> call = api.postAccount(getToken(), encrypt(login), encrypt(pass), encrypt(description));
        call.enqueue(new Callback<POST>() {
            @Override
            public void onResponse(Call<POST> call, Response<POST> response) {
                view.showNotification("Successfully sent!");
            }

            @Override
            public void onFailure(Call<POST> call, Throwable t) {
                view.showNotification("Error accrued!\n" + t.getLocalizedMessage());
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
