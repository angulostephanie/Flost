package com.specialtopics.flost.Models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String email, first, last, photoURL;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String first, String last, String photoURL) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.photoURL = photoURL;
    }
    public User(String email, String first, String last) {
        this.first = first;
        this.last = last;
        this.email = email;
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
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
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
    public String getPhotoURL() {
        return photoURL;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("first", first);
        result.put("last", last);
        result.put("email", email);
        result.put("photoURL", photoURL);
        return result;
    }

    //photoURL
    //user_first_name
    //user_last_name
    //email
}
