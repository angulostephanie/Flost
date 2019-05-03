package com.specialtopics.flost.Models;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private int itemID;
    private String name;
    private String desc;
    private String type;
    private String location;
    private String email;
    private String timeStamp;
    private double compensation;

    public Item(){}

    // when adding an item, timestamp and item id are generated for you
    // timestamps are generated on the server
    public Item(String email, String name, String desc, String type,
                String location, double compensation) {
        this.itemID = this.hashCode(); // based on the objects address in mem
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.email = email;
        this.compensation = compensation;
    }

    // when fetching the time, you would provide the timestamp/item id given from the db
    public Item(int itemID, String email, String name, String desc, String type,
                String location, String timestamp, double compensation) {
        this.itemID = itemID;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.email = email;
        this.timeStamp = timestamp;
        this.compensation = compensation;
    }

    public Item(String name, String desc, double compensation) {
        this.name = name;
        this.desc = desc;
        this.compensation = compensation;
    }

    public void setName(String name) { this.name = name; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setType(String type) { this.type = type; }
    public void setLocation(String location) { this.location = location; }
    public void setEmail(String email) { this.email = email; }
    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }
    public void setReward(double compensation) { this.compensation = compensation; }

    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public String getType() { return type; }
    public String getLocation() { return location; }
    public String getEmail() { return email; }
    public String getTimeStamp() { return timeStamp; }
    public double getReward() { return compensation; }

    public static List<Item> getTemporaryData(){
        List<Item> list = new ArrayList<>();

        list.add(new Item("Red Hydroflask", "Please find it!", 20.0));
        list.add(new Item("Pencil Case", "", 0));
        list.add(new Item("Yellow Hydroflask", "", 15.0));

        return list;
    }

    public String toString() {
        return itemID + ", " + email + ", " + name + ", " + desc + ", " + type + ", " + location
                + ", " + timeStamp +  ", "  + compensation;
    }
}
