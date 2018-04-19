package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Ford on 4/11/2018.
 */

public interface EntitySelectMVC {
    interface View extends Notifiable {
        /**
         * @param description - description of account that user defined
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
         * Asks server for current list of accounts
         */
        void getData();
    }
}
