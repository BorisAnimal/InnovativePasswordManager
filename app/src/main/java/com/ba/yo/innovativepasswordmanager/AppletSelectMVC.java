package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public interface AppletSelectMVC {
    interface View extends Notifiable{
        /**
         * @param description - description of applet
         * @param id          - hide it in element (such in 'tag' field). Use it in intent when item selected
         */
        void addApplet(String description, String id);

        /**
         * Remove all items from list
         */
        void clearList();

        /**
         * Go to initial screen after success data transaction
         */
        void onSuccessTransfer();
    }

    interface Controller {
        /**
         * Asks server for current list of applets
         */
        void getData();

        /**
         * Asks server to send login and password of selected account to the selected applet
         *
         * @param appletId  - Id of applet that will receive data
         * @param accountId - Id of user's account data that she/he decided to send
         */
        void sendDataToApplet(String appletId, String accountId);
    }
}
