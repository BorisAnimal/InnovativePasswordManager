package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public interface WipeDataMVC {
    interface View extends Notifiable, Transition {
        boolean isCheckboxChecked();

        String getMasterPassword();
    }

    interface Controller {
        void fullWipe();
    }
}
