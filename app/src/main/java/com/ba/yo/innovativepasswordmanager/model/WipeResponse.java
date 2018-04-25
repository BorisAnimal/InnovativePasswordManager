package com.ba.yo.innovativepasswordmanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public class WipeResponse {
    @SerializedName("accounts_deleted")
    private int accounts_deleted;

    public WipeResponse(int accounts_deleted) {
        this.accounts_deleted = accounts_deleted;
    }

    public int getAccounts_deleted() {

        return accounts_deleted;
    }

    public void setAccounts_deleted(int accounts_deleted) {
        this.accounts_deleted = accounts_deleted;
    }
}
