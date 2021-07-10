package com.example.instagram.models;

import android.app.Application;

import com.example.instagram.models.Comment;
import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Comment.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("zCFu2jvQo4tT99mGLIvyAIom4XUZSwc16bmfYCFs")
                .clientKey("4BpPzZQ7kyWYI0FIlCPXpLsDMYuDx55HH0bxq1q4")
                .server("https://parseapi.back4app.com")
                .build()
        );


    }

}
