package com.example.dungan_lakaw.cardviewnotes;

import java.io.Serializable;

public class NoteCards implements Serializable {
    private String title;
    private String text;

    public NoteCards(String title, String text) {
        this.title = title;
        this.text = text;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
