package com.example.mylibrary.tibbi.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylibrary.tibbi.Activities.LootActivity;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Activities.GameActivity;
import com.example.mylibrary.tibbi.Utils.MainCharacter;


public class FightResultFragment extends Fragment{
    private MainCharacter mChar;
    private int position;
    public FightResultFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            Intent intent = getActivity().getIntent();
            mChar = intent.getBundleExtra("STATS_BUNDLE").getParcelable("STATS");
            position = intent.getIntExtra("position",0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result_fight, container, false);
        TextView textView = rootView.findViewById(R.id.fragment_result_fight_TextView);
        Button button = rootView.findViewById(R.id.fragment_result_fight_Button);
        Bundle bundle = getArguments();
        final String text = bundle.getString("RESULT");

        if (text != null){
            textView.setText(text);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text == "DEFEAT!") {
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    intent.putExtra("CLASS", mChar.getC_class());
                    intent.putExtra("Flavor", mChar.getFlavor());
                    getActivity().finish();
                    startActivity(intent);
                }
                if (text == "VICTORY!"){
                    Intent intent = new Intent(getActivity(), LootActivity.class);
                    intent.putExtra("CHAR", (Parcelable) mChar);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}
