package com.ba.yo.innovativepasswordmanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public class AccountModel {
    @SerializedName("description")
    private String description;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountModel(String description, String login, String password, String id) {

        this.description = description;
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public AccountModel(String description, String login, String password) {
        this.description = description;
        this.login = login;
        this.password = password;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
