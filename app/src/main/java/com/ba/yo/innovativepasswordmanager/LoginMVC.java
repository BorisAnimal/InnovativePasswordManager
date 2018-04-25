package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public interface LoginMVC {
    interface View extends Notifiable, Transition {
        /**
         * Set values in fields
         */
        void setExisting(String login, String pasword);
    }

    interface Controller {
        /**
         * Get information from user and ask server if such user really exists in the system
         *
         * @param login    - login
         * @param password - password
         */
        void check(String login, String password);
    }

}
