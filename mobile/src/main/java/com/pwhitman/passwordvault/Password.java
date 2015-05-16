package com.pwhitman.passwordvault;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String mWebsite;
    private PasswordUtility mPassUtil;


    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_PASS = "pass";
    private static final String JSON_ID = "id";
    private static final String JSON_NOTES = "notes";
    private static final String JSON_USERNAME = "userName";
    private static final String JSON_WEBSITE = "website";

    public Password(){
        if(mId == null) mId = UUID.randomUUID();
        mCreationDate = new Date();
        if (mPassUtil == null) mPassUtil = PasswordUtility.getInstance();
    }

    public Password(JSONObject json)throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));

        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mCreationDate = new Date(json.getLong(JSON_DATE));
        if(json.has(JSON_PASS)) {
            mPass = json.getString(JSON_PASS);
        }
        if(json.has(JSON_NOTES)){
            mNotes = json.getString(JSON_NOTES);
        }
        if(json.has(JSON_USERNAME)){
            mUsername = json.getString(JSON_USERNAME);
        }
        if(json.has(JSON_WEBSITE)){
            mWebsite = json.getString(JSON_WEBSITE);
        }
        mPassUtil = PasswordUtility.getInstance();
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_DATE, mCreationDate.getTime());
        json.put(JSON_PASS, mPass);
        json.put(JSON_NOTES, mNotes);
        json.put(JSON_USERNAME, mUsername);
        json.put(JSON_WEBSITE, mWebsite);
        return json;
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
        //mPass = pass;
        if(pass == null || pass.trim().isEmpty()) return;
        mPass = mPassUtil.encryptString(pass);
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }


    public PasswordUtility getmPassUtil() {
        return mPassUtil;
    }

    public String getmWebsite() {
        return mWebsite;
    }

    public void setmWebsite(String mWebsite) {
        this.mWebsite = mWebsite;
    }
}
