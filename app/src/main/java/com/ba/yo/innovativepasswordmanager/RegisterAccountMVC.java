package com.ba.yo.innovativepasswordmanager;

public interface RegisterAccountMVC {

    interface View extends Notifiable {
        /**
         * Get value from login field
         * @return String value of login
         */
        String getLogin();

        /**
         * Get value from password field
         * @return String value of password
         */
        String getPassword();

        /**
         * Return to previous activity, i.e. LoginActivity
         */
        void returnToLoginScreen(String login, String password);
    }

    interface Controller {
        /**
         * Get information from user and ask server to register new account
         */
        void registerAccount();
    }

}
