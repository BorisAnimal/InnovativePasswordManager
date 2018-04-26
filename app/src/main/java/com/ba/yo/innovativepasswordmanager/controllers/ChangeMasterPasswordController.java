package com.ba.yo.innovativepasswordmanager.controllers;

import android.util.Log;

import com.ba.yo.innovativepasswordmanager.ChangeMasterPasswordMVP;
import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;
import com.ba.yo.innovativepasswordmanager.model.AccountModel;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Java-Ai-BOT on 4/23/2018.
 */

public class ChangeMasterPasswordController implements ChangeMasterPasswordMVP.Controller {
    private ChangeMasterPasswordMVP.View view;
    private ApiClient api;
    private final String TAG = "CHANGE_MP";

    public ChangeMasterPasswordController(ChangeMasterPasswordMVP.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    @Override
    public void changeMasterPassword() {
        //Checking
        final String oldMP = view.getOldMP();
        final String newMP = view.getNewMP();
        if (newMP == null || newMP.length() < 4 || !newMP.equals(view.getNewMPRepeat())) {
            view.showNotification("New password repeated wrong");
            return;
        }
        if (!CryptoCipher.checkMP(oldMP)) {
            view.showNotification("Wrong password!");
            return;
        }
        //get all data
        Call<List<AccountModel>> call = api.getDataDump(CryptoCipher.getToken());
        call.enqueue(new Callback<List<AccountModel>>() {
            @Override
            public void onResponse(Call<List<AccountModel>> call, Response<List<AccountModel>> response) {
                try {
                    Log.d(TAG, response.toString());
                    if (response.body() != null && response.code() == 200) {
                        Log.d(TAG, "List size: " + response.body().size());
                        List<AccountModel> entities = response.body();
                        Log.d(TAG, "Start deciphering:");
                        for (AccountModel a : entities) {
                            //decipher all data
                            a.setLogin(CryptoCipher.decrypt(a.getLogin()));
                            a.setPassword(CryptoCipher.decrypt(a.getPassword()));
                        }
                        //recipher all data
                        CryptoCipher.storeMP(newMP);
                        for (AccountModel a : entities) {
                            a.setLogin(CryptoCipher.encrypt(a.getLogin()));
                            a.setPassword(CryptoCipher.encrypt(a.getPassword()));
                        }
                        //send data and new hashed password to server
                        String accountsJson = new Gson().toJson(entities);
                        Call<ResponseBody> submitCall = api.changeMasterPassowrd(CryptoCipher.getToken(),
                                CryptoCipher.hash256(newMP), accountsJson);
                        submitCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.d(TAG, "Submit new password request:");
                                if (response.body() != null && response.code() == 200) {
                                    view.showNotification("Success");
                                    Log.d(TAG, "Response body: " + response.body().toString());
                                    view.goToEntitySelectActivity();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e(TAG, "Incorrect data submit call:\n" + t);
                                view.showNotification("Something gone wrong!\n" + t);
                            }
                        });
                    }
                } catch (CryptoUtils.DecryptionException ex) {
                    Log.e(TAG, "Decryption exception: " + ex.getLocalizedMessage());
                    view.showNotification("Crypto exception");
                } catch (NoSuchAlgorithmException ex) {
                    Log.e(TAG, "Security error: " + ex);
                    view.showNotification("Crypto exception");
                }
            }

            @Override
            public void onFailure(Call<List<AccountModel>> call, Throwable t) {
                Log.e(TAG, "Incorrect data dump call:\n" + t);
                view.showNotification("Something gone wrong!\n" + t);
            }
        });
    }
}
