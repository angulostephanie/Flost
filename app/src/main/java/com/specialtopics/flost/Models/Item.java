package com.specialtopics.flost.Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Item {

    private String itemID;
    private String name;
    private String desc;
    private String type;
    private String location;
    private String userID;
    private String timeSteamp;
    private boolean compensation;

    public Item(){};

    public Item(String itemID, String name, String desc, String type, String location, String userID, boolean compensation){
        this.itemID = itemID;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.userID = userID;
        this.timeSteamp = createTimestamp();
        this.compensation = compensation;
    }

    public Item(String name, String desc, boolean compensation){
        this.name = name;
        this.desc = desc;
        this.compensation = compensation;
    }

    public void setItemID(String itemID) { this.itemID = itemID; }
    public void setName(String name) { this.name = name; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setType(String type) { this.type = type; }
    public void setLocation(String location) { this.location = location; }
    public void setUserID(String userID) { this.userID = userID; }
    public void setTimeSteamp(String timeSteamp) { this.timeSteamp = timeSteamp; }


    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public String getType() { return type; }
    public String getLocation() { return location; }public String getUserID() { return userID; }
    public String getTimeSteamp() { return timeSteamp; }

    //item_id - root
    //item_name
    //item_desc
    //item_type
    //item_reward
    //item_location
    //user_id
    //timestamp

    public String createTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String timestamp = now.toString();
        return timestamp;
    }

    public static List<Item> getTemporaryData(){
        List<Item> list = new ArrayList<>();

        list.add(new Item("Red Hydroflask", "Please find it!", true));
        list.add(new Item("Pencil Case", "", false));
        list.add(new Item("Yellow Hydroflask", "", true));

        return list;
    }
}
