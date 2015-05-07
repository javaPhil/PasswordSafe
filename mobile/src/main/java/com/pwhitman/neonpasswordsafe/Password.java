package com.pwhitman.neonpasswordsafe;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Philip on 1/2/2015.
 */
public class Password {

    private String mTitle;
    private Date mCreationDate;
    private String mPass;
    private UUID mId;
    private String mNotes;
    private String mUsername;

    public Password(){
        mId = UUID.randomUUID();
        mCreationDate = new Date();
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }


}
