package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public interface LoginMVC {
    interface View extends Notifiable {
        /**
         * User is identified and he/she can begin using application.
         * All needed values stored on session time.
         */
        void acceptVerification();
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
