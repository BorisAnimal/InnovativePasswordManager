package com.ba.yo.innovativepasswordmanager.controllers;

import com.ba.yo.innovativepasswordmanager.RegisterAccountMVC;

public class RegisterAccountController implements RegisterAccountMVC.Controller {

    private RegisterAccountMVC.View view;

    //TODO: go back to login via view.returnToLoginScreen()

    public RegisterAccountController(RegisterAccountMVC.View view){
        this.view = view;
    }

    @Override
    public void registerAccount() {
        String login = view.getLogin();
        String password = view.getPassword();
    }

}
