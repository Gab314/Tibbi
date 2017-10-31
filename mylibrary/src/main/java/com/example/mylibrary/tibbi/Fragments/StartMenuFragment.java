package com.example.mylibrary.tibbi.Fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Activities.ClassSelectionActivity;
import com.example.mylibrary.tibbi.Activities.GameActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;


public class StartMenuFragment extends Fragment{

    private FirebaseAnalytics mFirebaseAnalytics;
    private String flavor;
    private AdView adView;
    private AdRequest adRequest;
    public StartMenuFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            Intent intent = getActivity().getIntent();
            flavor = intent.getStringExtra("Flavor");
        }
        if (savedInstanceState != null){
            flavor = savedInstanceState.getString("Flavor");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());



        View rootview = inflater.inflate(R.layout.start_menu, container, false);
        final Button start_button =  rootview.findViewById(R.id.start_btn_start);
        final Button inst_button = rootview.findViewById(R.id.start_btn_Instructions);

        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent start_intent = new Intent(getActivity(), ClassSelectionActivity.class);
                start_intent.putExtra("Flavor", flavor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(start_intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }else startActivity(start_intent);

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "start");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });

        inst_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionsFragment fragment = new InstructionsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.start_menu_RelativeLayout, fragment);
                fragmentTransaction.commit();
            }
        });

            adView = rootview.findViewById(R.id.adView);
            adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                    //.addTestDevice("")
                    .build();
            adView.loadAd(adRequest);



            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.i("Ads", "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.i("Ads", "onAdFailedToLoad");
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.i("Ads", "onAdOpened");
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    Log.i("Ads", "onAdLeftApplication");
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                    Log.i("Ads", "onAdClosed");
                }
            });

        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Flavor", flavor);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();

        }
        super.onPause();
    }

    @Override
   public void onResume() {
        super.onResume();
        if(adView!=null){  // Check if Adview is not null in case of fist time load.
            adView.resume();}
    }
}
