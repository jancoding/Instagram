package com.example.instagram.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.PostsFragment;
import com.example.instagram.R;
import com.example.instagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private File photoFile;
    private String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new PostsFragment();
        fragmentManager.beginTransaction().replace(R.id.rlMain, fragment).commit();



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_favorites:
                        fragment = new PostsFragment();
                        fragmentManager.beginTransaction().replace(R.id.rlMain, fragment).commit();
                        break;
                    case R.id.action_schedules:
                        fragment = new ComposeFragment();
                        fragmentManager.beginTransaction().replace(R.id.rlMain, fragment).commit();
                        break;
                    case R.id.action_music:
                        fragment = new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.rlMain, fragment).commit();

                    default:
                        break;
                }
                return true;
            }
        });
    }


}