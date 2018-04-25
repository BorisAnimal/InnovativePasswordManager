package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public interface WipeDataMVC {
    interface View extends Notifiable, Transition {
        /**
         * Check if user has confirmed full wipe
         * @return True if he did
         */
        boolean allowedToWipe();

        /**
         * Return value from master password field that will need to be checked in order to do a complete wipe
         * @return String value of password field
         */
        String getMasterPassword();

        /**
         * Hide/show elements on page so that user could not proceed wiping data without confirnment
         * @param state True to set visible, False to hide
         * @return
         */
        void setElementsVisibility(boolean state);
    }

    interface Controller {
        void fullWipe();
        void confirmWipe(boolean state);
    }
}
