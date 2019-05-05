package com.specialtopics.flost.Models;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.specialtopics.flost.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item implements Parcelable {

    private int itemID;
    private String name;
    private String desc;
    private String type;
    private String location;
    private String email;
    private String timeStamp;
    private boolean containsStaticImage;
    private byte[] image;
    private int staticImageID;
    private String inputTime;
    private String inputDay;

    public Item(){
        this.itemID = 0;
        this.name = "";
        this.desc = "";
        this.type = "";
        this.location = "";
        this.timeStamp = "";
        this.inputTime = "";
        this.inputDay = "";
    };

    /*
       THE NEXT TWO CONSTRUCTORS ARE USED WHEN CREATING AN ITEM
        This constructor is used when the item is mapped to a static image.
     */

    public Item(String email, String name, String desc, String type,
                String location, int staticImageID) {
        this.itemID = this.hashCode(); // based on the objects address in mem
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.email = email;
        this.containsStaticImage = true;
        this.staticImageID = staticImageID;

    }

    /*
        This constructor is used when the user uploads their own photo
     */

    public Item(String email, String name, String desc, String type,
                String location, byte[] image) {
        this.itemID = this.hashCode(); // based on the objects address in mem
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.email = email;
        this.containsStaticImage = false;
        this.image = image;
    }

    /*
        THE NEXT TWO CONSTRUCTORS ARE USED WHEN FETCHING ITEMS FROM DB
        When fetching the item, you would provide the timestamp/item id given from the db
     */

    public Item(int itemID, String email, String name, String desc, String type,
                String location, String timestamp, int staticImageID) {
        this.itemID = itemID;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.inputTime = inputTime;
        this.inputDay = inputDay;
        this.email = email;
        this.timeStamp = timestamp;
        this.staticImageID = staticImageID;
        this.containsStaticImage = true;
    }

    public Item(int itemID, String email, String name, String desc, String type,
                String location, String timestamp, byte[] image) {
        this.itemID = itemID;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.location = location;
        this.timeStamp = timestamp;
        this.email = email;
        this.image = image;
        this.containsStaticImage = false;
    }

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public void setName(String name) { this.name = name; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setType(String type) { this.type = type; }
    public void setLocation(String location) { this.location = location; }
    public void setInputTime(String inputTime) {this.inputTime = inputTime; }
    public void setInputDay(String inputDay) { this.inputDay = inputDay; }
    public void setEmail(String email) { this.email = email; }
    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }
    public void setImage(byte[] image) { this.image = image; }
    public void setStaticImageID(int staticImageID) { this.staticImageID = staticImageID; }


    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public String getType() { return type; }
    public String getInputTime() { return inputTime; }
    public String getInputDay() { return inputDay; }
    public String getLocation() { return location; }
    public String getEmail() { return email; }
    public String getTimeStamp() { return timeStamp; }
    public byte[] getImage() { return image; }
    public int getStaticImageID() { return staticImageID; }
    public boolean containsStaticImage() { return containsStaticImage; }

    public int hashCode() {
        return Math.abs(Objects.hash(name, desc, type,
                inputDay, inputTime, location, email, containsStaticImage, System.currentTimeMillis()));
    }
    public static List<Item> getTemporaryData(){
        List<Item> list = new ArrayList<>();

        list.add(new Item("Red Hydroflask", "Please find it!"));
        list.add(new Item("Pencil Case", ""));
        list.add(new Item("Yellow Hydroflask", ""));

        return list;
    }

    public Item(Parcel in){
        itemID = in.readInt();
        name = in.readString();
        desc = in.readString();
        type = in.readString();
        location = in.readString();
        email = in.readString();
        timeStamp = in.readString();
        containsStaticImage = (boolean) in.readValue(null);
        byte[] value = new byte[in.readInt()];
        in.readByteArray(this.image);
        staticImageID = in.readInt();
        inputTime = in.readString();
        inputDay = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(itemID);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(type);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeString(timeStamp);
        dest.writeValue(containsStaticImage);
        dest.writeByteArray(image);
        dest.writeInt(staticImageID);
        dest.writeString(inputTime);
        dest.writeString(inputDay);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String toString() {
        return itemID + ", " + email + ", " + name + ", " + desc + ", " + type + ", " + location
                + ", " + timeStamp + ", " + staticImageID;
    }

    public static byte[] createTestByteArray(Context mContext) {
        Drawable d = mContext.getDrawable(R.drawable.bottle);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
}