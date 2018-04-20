package com.ba.yo.innovativepasswordmanager.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

public class AppletDescriptionModel {
    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("error_msg")
    private String error;

    public AppletDescriptionModel(String description, String id, String error) {
        this.description = description;
        this.id = id;
        this.error = error;
    }

    public AppletDescriptionModel(String description, String id) {

        this.description = description;
        this.id = id;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
