package com.specialtopics.flost.Models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userID, email, first, last;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, String email, String first, String last) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.userID = userID;
    }

    public void setFirst(String first) {
        this.first = first;
    }
    public void setLast(String last) {
        this.last = last;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirst() {
        return first;
    }
    public String getLast() {
        return last;
    }
    public String getEmail() {
        return email;
    }
    public String getUserID() {
        return userID;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("first", first);
        result.put("last", last);
        result.put("email", email);
        result.put("userID", userID);
        return result;
    }

    //user_id
    //user_first_name
    //user_last_name
    //email
}
