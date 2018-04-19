package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public interface AppletSelectMVC {
    interface View {
        /**
         * @param description - description of applet
         * @param id          - hide it in element (such in 'tag' field). Use it in intent when item selected
         */
        void addEntity(String description, String id);

        /**
         * Remove all items from list
         */
        void clearList();
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
