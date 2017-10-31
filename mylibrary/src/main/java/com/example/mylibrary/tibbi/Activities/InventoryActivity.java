package com.example.mylibrary.tibbi.Activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Window;

import com.example.mylibrary.tibbi.Fragments.InventoryFragment;
import com.example.mylibrary.tibbi.R;

public class InventoryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Fade());
        }

        setContentView(R.layout.activity_game);

        if (savedInstanceState == null) {
            InventoryFragment fragment = new InventoryFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_game_RelativeLayout, fragment);
            fragmentTransaction.commit();
        }
    }
}
