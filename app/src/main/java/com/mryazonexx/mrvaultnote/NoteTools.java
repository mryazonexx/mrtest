package com.mryazonexx.mrvaultnote;

import com.google.firebase.Timestamp;

public class NoteTools {
    String title;
    String content;
    Timestamp timestamp;


    public NoteTools() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
