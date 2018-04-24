package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public interface LoginMVC {
    interface View extends Notifiable, Transition {

    }

    interface Controller {
        /**
         * Get information from user and asks server if such user really exists in our system
         *
         * @param login    - login
         * @param password - password
         */
        void check(String login, String password);
    }

}
