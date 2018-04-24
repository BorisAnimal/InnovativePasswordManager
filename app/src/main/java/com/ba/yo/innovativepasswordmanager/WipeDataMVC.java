package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public interface WipeDataMVC {
    interface View extends Notifiable, Transition {

    }

    interface Controller {
        /**
         *
         * @param password Master password confirmation
         */
        void fullWipe(String password);
    }
}
