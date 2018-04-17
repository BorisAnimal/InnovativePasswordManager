package com.ba.yo.innovativepasswordmanager.controllers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class LoginResponseModel {
    @SerializedName("verified")
    private Boolean verified;
    @SerializedName("token")
    private String sessionToken;

    public LoginResponseModel(Boolean verified, String sessionToken) {
        this.verified = verified;
        this.sessionToken = sessionToken;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

}
