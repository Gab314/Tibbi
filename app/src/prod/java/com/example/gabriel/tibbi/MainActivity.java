package com.example.gabriel.tibbi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mylibrary.tibbi.Activities.LibMainActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchLibraryActivity();
    }

    public void launchLibraryActivity() {
        Intent myIntent = new Intent(this, LibMainActivity.class);
        myIntent.putExtra("Flavor","prod");
        startActivity(myIntent);
    }
}
