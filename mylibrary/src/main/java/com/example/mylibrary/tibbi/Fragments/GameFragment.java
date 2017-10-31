package com.example.mylibrary.tibbi.Fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.tibbi.Activities.InventoryActivity;
import com.example.mylibrary.tibbi.Data.DBContract;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Activities.LadderActivity;
import com.example.mylibrary.tibbi.Utils.MainCharacter;
import com.example.mylibrary.tibbi.Widget.IntentService;


public class GameFragment extends Fragment{

    private MainCharacter mChar1, character;
    private String char_class, flavor, weapon;
    public GameFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Intent intent = getActivity().getIntent();
            char_class = intent.getStringExtra("CLASS");
            flavor = intent.getStringExtra("Flavor");
            weapon = intent.getStringExtra("WEAPON");
            character = intent.getParcelableExtra("char");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState == null && flavor != null && char_class != null){
            mChar1 = new MainCharacter(char_class, flavor);
        }
        if (character != null){
            mChar1 = character;
        }
        if (savedInstanceState != null){
            mChar1 = (MainCharacter) savedInstanceState.getSerializable("SAVED_CHAR");
            flavor = savedInstanceState.getString("Flavor");
        }


        View rootview = inflater.inflate(R.layout.game_main_menu, container, false);
        final TextView mHP =  rootview.findViewById(R.id.activity_game_TextViewHP);
        final TextView mWIL =  rootview.findViewById(R.id.activity_game_TextViewWIL);
        final TextView mAGI =  rootview.findViewById(R.id.activity_game_TextViewAGI);
        final TextView mDEX =  rootview.findViewById(R.id.activity_game_TextViewDEX);
        final TextView mDET =  rootview.findViewById(R.id.activity_game_TextViewDET);
        final TextView mREF =  rootview.findViewById(R.id.activity_game_TextViewREF);
        final TextView mLUC =  rootview.findViewById(R.id.activity_game_TextViewLUC);

        Button bHP = rootview.findViewById(R.id.add_HP);
        Button bWIL = rootview.findViewById(R.id.add_WIL);
        Button bAGI = rootview.findViewById(R.id.add_AGI);
        Button bDEX = rootview.findViewById(R.id.add_DEX);
        Button bDET = rootview.findViewById(R.id.add_DET);
        Button bREF = rootview.findViewById(R.id.add_REF);
        Button bLUC = rootview.findViewById(R.id.add_LUC);


        if (flavor.equals("qa")){
            bHP.setVisibility(View.VISIBLE);
            bWIL.setVisibility(View.VISIBLE);
            bAGI.setVisibility(View.VISIBLE);
            bDEX.setVisibility(View.VISIBLE);
            bDET.setVisibility(View.VISIBLE);
            bREF.setVisibility(View.VISIBLE);
            bLUC.setVisibility(View.VISIBLE);

            bHP.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_HP();
                    String c_hp = getResources().getString(R.string.hp)  + String.valueOf(mChar1.getTotal_HP());
                    mHP.setText(c_hp);
                }
            });
            bWIL.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_WIL();
                    String c_wil = getResources().getString(R.string.wil)  + String.valueOf(mChar1.getTotal_WIL());
                    mWIL.setText(c_wil);
                }
            });
            bAGI.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_AGI();
                    String c_agi = getResources().getString(R.string.agi)  + String.valueOf(mChar1.getTotal_AGI());
                    mAGI.setText(c_agi);
                }
            });
            bDEX.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_DEX();
                    String c_dex = getResources().getString(R.string.dex)  + String.valueOf(mChar1.getTotal_DEX());
                    mDEX.setText(c_dex);
                }
            });
            bDET.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_DET();
                    String c_det = getResources().getString(R.string.det)  + String.valueOf(mChar1.getTotal_DET());
                    mDET.setText(c_det);
                }
            });
            bREF.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_REF();
                    String c_ref = getResources().getString(R.string.ref)  + String.valueOf(mChar1.getTotal_REF());
                    mREF.setText(c_ref);
                }
            });
            bLUC.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mChar1.increase_LUC();
                    String c_luc = getResources().getString(R.string.luc)  + String.valueOf(mChar1.getTotal_LUC());
                    mLUC.setText(c_luc);
                }
            });
        }


        if (weapon != null){
            Toast.makeText(getActivity(), weapon, Toast.LENGTH_SHORT).show();
        }

        String c_hp = getResources().getString(R.string.hp)  + String.valueOf(mChar1.getTotal_HP());
        String c_wil = getResources().getString(R.string.wil)  + String.valueOf(mChar1.getTotal_WIL());
        String c_agi = getResources().getString(R.string.agi)  + String.valueOf(mChar1.getTotal_AGI());
        String c_dex = getResources().getString(R.string.dex)  + String.valueOf(mChar1.getTotal_DEX());
        String c_det = getResources().getString(R.string.det)  + String.valueOf(mChar1.getTotal_DET());
        String c_ref = getResources().getString(R.string.ref)  + String.valueOf(mChar1.getTotal_REF());
        String c_luc = getResources().getString(R.string.luc)  + String.valueOf(mChar1.getTotal_LUC());



        mHP.setText(c_hp);
        mWIL.setText(c_wil);
        mAGI.setText(c_agi);
        mDEX.setText(c_dex);
        mDET.setText(c_det);
        mREF.setText(c_ref);
        mLUC.setText(c_luc);

        final Button fight_button = rootview.findViewById(R.id.activity_game_Button_fight);
        final Button inv_button = rootview.findViewById(R.id.activity_game_Button_loot);

        fight_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent start_intent = new Intent(getActivity(), LadderActivity.class);
                start_intent.putExtra("char", (Parcelable) mChar1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(start_intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }else startActivity(start_intent);
            }
        });

        inv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start_intent = new Intent(getActivity(), InventoryActivity.class);
                start_intent.putExtra("char", (Parcelable) mChar1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(start_intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }else startActivity(start_intent);
            }
        });

        IntentService.startActionPutStats(getActivity(), mChar1);

        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Flavor", flavor);
        savedInstanceState.putSerializable("SAVED_CHAR",mChar1);
    }

}
