package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Comment")
public class Comment extends ParseObject implements Serializable {
    public static final String KEY_TEXT = "text";
    public static final String KEY_POST = "post";
    public static final String KEY_USER = "user";

    public String getText() {
        return getString(KEY_TEXT);
    }

    public void setText(String description) {
        put(KEY_TEXT, description);
    }


    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public ParseObject getPost() {
        return getParseObject(KEY_POST);
    }

    public void setPost(ParseObject post) {
        put(KEY_POST, post);
    }
}
