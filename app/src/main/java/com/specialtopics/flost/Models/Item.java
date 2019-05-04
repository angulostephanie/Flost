package com.specialtopics.flost.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Item implements Parcelable {

    private String itemID;
    private String name;
    private String desc;
    private String type;
    private String location;
    private String userID;
    private String timeStamp;
    private boolean compensation;
    private String inputTime;
    private String inputDay;

    public Item(){
        this.itemID = "";
        this.name = "";
        this.desc = "";
        this.type = "";
        this.location = "";
        this.userID = "";
        this.timeStamp = "";
        this.compensation = false;
        this.inputTime = "";
        this.inputDay = "";
    };

    public Item(String itemID, String name, String desc, String type, String location, String userID, boolean compensation, String inputTime, String inputDay){
        this.itemID = itemID;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.userID = userID;
        this.timeStamp = createTimestamp();
        this.compensation = compensation;
        this.inputTime = inputTime;
        this.inputDay = inputDay;
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
    public void setTimeSteamp(String timeSteamp) { this.timeStamp = timeSteamp; }
    public void setInputTime(String inputTime) {this.inputTime = inputTime; }
    public void setInputDay(String inputDay) { this.inputDay = inputDay; }


    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public String getType() { return type; }
    public String getLocation() { return location; }public String getUserID() { return userID; }
    public String getTimeSteamp() { return timeStamp; }
    public String getInputTime() { return inputTime; }
    public String getInputDay() { return inputDay; }

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
    public Item(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.itemID = data[0];
        this.name = data[1];
        this.desc = data[2];
        this.type = data[3];
        this.location = data[4];
        this.userID = data[5];
        this.timeStamp = data[6];
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.itemID,
                this.name,
                this.desc,
        this.type,
        this.location,
        this.userID,
        this.timeStamp});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
