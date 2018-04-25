package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;
import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.model.AccountModel;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher.encrypt;
import static com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher.getToken;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class EditEntryController implements EditEntryMVC.Controller {
    private EditEntryMVC.View view;
    private ApiClient api;
    private AccountModel model;
    private final String TAG = "EDIT_ENTITY";


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
    public EditEntryController(final EditEntryMVC.View view, String id) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);

        Call<AccountModel> call = api.getAccount(getToken(), id);
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    model = response.body();
                    try {
                        Log.d(TAG, model.getLogin());
                        String login = CryptoCipher.decrypt(model.getLogin());
                        Log.d(TAG, login);
                        Log.d(TAG, model.getPassword());
                        String password = CryptoCipher.decrypt(model.getPassword());
                        Log.d(TAG, password);
                        view.setExisting(login, password, model.getDescription());
                    } catch (CryptoUtils.DecryptionException e) {
                        Log.e(TAG, e + "");
                        view.showNotification("Crypto error");
                    }
                } else
                    view.showNotification("Error occurred: " + response.code());
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                view.showNotification("Error occurred!\n" + t.getLocalizedMessage());
                Log.e("EditEntity", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void commitEntry(String login, String pass, String description) {
        //We post new model instance
        if (model == null) {
            model = new AccountModel();
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
        Call<ResponseBody> call;
        if (model.getId() != "") {
            call = api.postAccount(getToken(), encrypt(model.getLogin()),
                    encrypt(model.getPassword()), model.getDescription(), model.getId());
        } else {
            call = api.postAccount(getToken(), encrypt(model.getLogin()),
                    encrypt(model.getPassword()), model.getDescription());
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    view.showNotification("Successfully sent!");
                    view.goToEntitySelectActivity();
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
