package com.specialtopics.flost.Models;

public class ChatRoom {
    private int id;
    private String ownerEmail;

    public ChatRoom() {

    }

    public ChatRoom(int id, String ownerEmail) {
        this.id = id;
        this.ownerEmail = ownerEmail;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public int getID() {
        return id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

}

