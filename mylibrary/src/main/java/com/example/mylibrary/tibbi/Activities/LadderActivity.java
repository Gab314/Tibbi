package com.example.mylibrary.tibbi.Activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Window;

import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Fragments.LadderFragment;

public class LadderActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Fade());
        }
        setContentView(R.layout.activity_ladder);

        if (savedInstanceState == null) {
            LadderFragment fragment = new LadderFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_ladder_RelativeLayout, fragment);
            fragmentTransaction.commit();
        }

    }
}
