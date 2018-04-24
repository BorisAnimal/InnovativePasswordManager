package com.ba.yo.innovativepasswordmanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public class WipeResponse {
    @SerializedName("applets_deleted")
    private int applets_deleted;
    @SerializedName("accounts_deleted")
    private int accounts_deleted;

    public int getApplets_deleted() {
        return applets_deleted;
    }

    public void setApplets_deleted(int applets_deleted) {
        this.applets_deleted = applets_deleted;
    }

    public int getAccounts_deleted() {
        return accounts_deleted;
    }

    public void setAccounts_deleted(int accounts_deleted) {
        this.accounts_deleted = accounts_deleted;
    }

    public WipeResponse(int applets_deleted, int accounts_deleted) {

        this.applets_deleted = applets_deleted;
        this.accounts_deleted = accounts_deleted;
    }

}
