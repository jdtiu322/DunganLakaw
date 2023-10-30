package com.example.dungan_lakaw.posts;

import java.io.Serializable;

public class Postcards implements Serializable {
    String username;
    String text;
    int upvotes, downvotes;

    public Postcards(String username, String text, int upvotes, int downvotes) {
        this.username = username;
        this.text = text;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }


    public Postcards() {
    }

    public Postcards(String s, String s1) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}
