package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/20/2018.
 */

public interface ChangeMasterPasswordMVP {
    interface View extends Notifiable, Transition {
        String getOldMP();

        String getNewMP();

        String getNewMPRepeat();

    }

    interface Controller {
        void changeMasterPassword();
    }
}
