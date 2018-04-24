package com.ba.yo.innovativepasswordmanager;

public interface DumpDataMVC {
    interface View extends Notifiable, Transition {

    }

    interface Controller {
        /**
         * Make complete dump of user data
         * @param password master password for confirmation
         */
        void makeDump(String password);
    }
}
