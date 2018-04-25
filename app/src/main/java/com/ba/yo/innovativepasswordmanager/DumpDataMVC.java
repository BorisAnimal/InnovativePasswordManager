package com.ba.yo.innovativepasswordmanager;

public interface DumpDataMVC {
    interface View extends Notifiable, Transition {
        String getPassword();
        boolean checkPermission();
        void askPermission();
    }

    interface Controller {
        /**
         * Make complete dump of user data
         */
        void makeDump();
    }
}
