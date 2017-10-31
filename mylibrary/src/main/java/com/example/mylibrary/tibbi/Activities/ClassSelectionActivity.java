package com.example.mylibrary.tibbi.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Fragments.ClassSelectionFragment;

public class ClassSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ClassSelectionFragment fragment = new ClassSelectionFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, fragment);
        fragmentTransaction.commit();
    }
}
