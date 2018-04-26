package com.ba.yo.innovativepasswordmanager.ui;

public class AppletEntry {

    private int apImageDrawable;
    private String apName, apId;

    /**
     * Container for Applet entry in list
     */
    public AppletEntry(int aImageDrawable, String aName, String aId){
        this.apImageDrawable = aImageDrawable;
        this.apName = aName;
        this.apId = aId;
    }

    public int getapImageDrawable() {
        return apImageDrawable;
    }

    public void setapImageDrawable(int aImageDrawable) {
        this.apImageDrawable = aImageDrawable;
    }

    public String getapName() {
        return apName;
    }

    public void setapName(String aName) {
        this.apName = aName;
    }

    public String getapId() {
        return apId;
    }

    public void setapId(String aId) {
        this.apId = aId;
    }
}
