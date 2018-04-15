package com.ba.yo.innovativepasswordmanager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Java-Ai-BOT on 4/11/2018.
 */

public class EntitySelectModel {
    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public EntitySelectModel(String description, String id) {
        this.description = description;
        this.id = id;
    }
}
