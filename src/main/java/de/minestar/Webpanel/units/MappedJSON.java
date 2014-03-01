package de.minestar.Webpanel.units;

import java.util.HashMap;

public class MappedJSON {

    private HashMap<String, String> data;

    public MappedJSON() {
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getValue(String name) {
        return data.get(name);
    }
}
