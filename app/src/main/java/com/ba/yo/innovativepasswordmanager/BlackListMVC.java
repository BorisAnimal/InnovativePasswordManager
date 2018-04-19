package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public interface BlackListMVC {
    interface View {
        /**
         * @param id - identifier of element
         */
        void removeFromBlockedList(String id);

        void removeFromWhiteList(String id);

        void addToBlockedList(String id, String description);

        /**
         * @param id          - identifier of element
         * @param description - text that user see element
         */
        void addToWhiteList(String id, String description);

        /**
         * Remove all items from view's list
         */
        void clearList();
    }

    interface Controller {
        void getData();

        /**
         * controller will remove applet from WhiteList! and move it to BlackList!
         *
         * @param id - identifier of element
         */
        void addToBlacklist(String id);

        /**
         * controller will remove applet from BlackList! and move it to WhiteList!
         *
         * @param id - identifier of element
         */
        void addToWhiteList(String id);
    }
}
