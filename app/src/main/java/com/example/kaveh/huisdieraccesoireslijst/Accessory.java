package com.example.kaveh.huisdieraccesoireslijst;

/**
 * Created by Kaveh on 10-3-2016.
 */
public class Accessory {
    private long id;
    private String accessory;
    private String website;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return accessory;
    }
}
