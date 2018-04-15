package com.ba.yo.innovativepasswordmanager;

public class AuthEntry {

    private int aImageDrawable;
    private String aName, aId;

    public AuthEntry(int aImageDrawable, String aName, String aId){
        this.aImageDrawable = aImageDrawable;
        this.aName = aName;
        this.aId = aId;
    }

    public int getaImageDrawable() {
        return aImageDrawable;
    }

    public void setaImageDrawable(int aImageDrawable) {
        this.aImageDrawable = aImageDrawable;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }
}
