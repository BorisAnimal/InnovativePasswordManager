package com.ba.yo.innovativepasswordmanager;

/**
 * Created by Ford on 4/11/2018.
 */

public interface Notifiable {
    /**
     * informs user about something unpredicted behaviour
     *
     * @param message - string that user should read
     */
    void showNotification(String message);
}
