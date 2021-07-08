package com.example.instagram;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

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
