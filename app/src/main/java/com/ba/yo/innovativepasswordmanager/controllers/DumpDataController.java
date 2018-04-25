package com.ba.yo.innovativepasswordmanager.controllers;

import com.ba.yo.innovativepasswordmanager.DumpDataMVC;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

public class DumpDataController implements DumpDataMVC.Controller {
    private DumpDataMVC.View view;
    private ApiClient api;

    public DumpDataController(DumpDataMVC.View view) {
        this.view = view;
        api = RetrofitService.getInstance().create(ApiClient.class);
    }

    /**
     * Make complete dump of user data
     *
     * @param password master password for confirmation
     */
    @Override
    public void makeDump(String password) {
        //TODO: implement method
    }
}
