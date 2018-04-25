package com.ba.yo.innovativepasswordmanager.controllers;

import android.os.Environment;
import android.util.Log;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;
import com.ba.yo.innovativepasswordmanager.DumpDataMVC;
import com.ba.yo.innovativepasswordmanager.model.AccountModel;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class DumpDataController implements DumpDataMVC.Controller {
    private DumpDataMVC.View view;
    private ApiClient api;
    private final String TAG = "DUMP_DATA";

    public DumpDataController(DumpDataMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    /**
     * Make complete dump of user data
     */
    @Override
    public void makeDump() {
        String password = view.getPassword();
        if (password == null) {
            view.showNotification("Enter correct password!");
            return;
        }
        if (!CryptoCipher.checkMP(password)) {
            view.showNotification("Wrong password");
            return;
        }
        retrofit2.Call<List<AccountModel>> call = api.getDataDump(CryptoCipher.getToken());
        call.enqueue(new Callback<List<AccountModel>>() {
            @Override
            public void onResponse(retrofit2.Call<List<AccountModel>> call, Response<List<AccountModel>> response) {
                Log.d(TAG, response.toString());
                try {
                    if (response.body() != null && response.code() == 200) {
                        Log.d(TAG, "List size: " + response.body().size());
                        List<AccountModel> entities = response.body();
                        Log.d(TAG, "Start deciphering:");
                        StringBuilder sb = new StringBuilder(entities.size());
                        for (AccountModel a : entities) {
                            //decipher all data
                            a.setLogin(CryptoCipher.decrypt(a.getLogin()));
                            a.setPassword(CryptoCipher.decrypt(a.getPassword()));
                        }
                        try {
                            if (!view.checkPermission())
                                view.askPermission();
                            String txt = new Gson().toJson(entities);
                            Log.d(TAG, txt);
                            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                    + "/dump.txt";
                            Log.d(TAG, "Path: " + path);
                            File file = new File(path);
                            if (file.exists())
                                file.delete();
                            FileOutputStream stream = new FileOutputStream(file);
                            stream.write(txt.getBytes());
                            stream.flush();
                            stream.close();
                            view.showNotification("Saved to Downloads");
                            view.goToEntitySelectActivity();
                        } catch (IOException e) {
                            view.showNotification("Error while writing to file\n" + e.getLocalizedMessage());
                            Log.e(TAG, e.getLocalizedMessage() + "");
                        }
                    }
                } catch (CryptoUtils.DecryptionException ex) {
                    Log.e(TAG, "Decryption exception: " + ex.getLocalizedMessage());
                    view.showNotification("Crypto exception");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<AccountModel>> call, Throwable t) {
                Log.e(TAG, "Incorrect data dump call:\n" + t);
                view.showNotification("Something gone wrong!\n" + t);
            }
        });
    }
}
