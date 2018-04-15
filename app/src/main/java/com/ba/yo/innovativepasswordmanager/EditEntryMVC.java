package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public interface EditEntryMVC {
    interface View extends Notifiable {
        /**
         * Appends password to corresponded field
         *
         * @param pass - target value
         */
//        void setPassword(String pass);
//
//        /**
//         * Appends login to corresponded field
//         *
//         * @param login - target value
//         */
//        void setLogin(String login);
//
//        /**
//         * Appends description to corresponded field
//         *
//         * @param description - target value
//         */
//        void setDescription(String description);
    }

    interface Controller {
        /**
         * Gets raw information (utf-8 strings), checks correctness, then encodes them and
         * sends to server.
         *
         * @param login       - string-login of added account
         * @param pass        - string-pass of added account
         * @param description - user-defined information of account from user. Will appear as
         *                    name in EntitySelectActivity
         */
        void commitEntry(String login, String pass, String description);

        /**
         * Generates and sets on View randomly generated password value
         */
        void generateRandomPassword();
    }
}
