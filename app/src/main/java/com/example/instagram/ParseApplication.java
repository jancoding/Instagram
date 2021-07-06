package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("zCFu2jvQo4tT99mGLIvyAIom4XUZSwc16bmfYCFs")
                .clientKey("4BpPzZQ7kyWYI0FIlCPXpLsDMYuDx55HH0bxq1q4")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
