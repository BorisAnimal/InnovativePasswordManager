package com.ba.yo.innovativepasswordmanager.controllers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class EntryModel {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("login")
    private String login;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("description")
    private String description;

    public EntryModel(String id, String login, String password, String description) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.description = description;
    }

    public EntryModel() {
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
