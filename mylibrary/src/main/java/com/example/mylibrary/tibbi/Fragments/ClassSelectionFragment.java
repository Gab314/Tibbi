package com.example.mylibrary.tibbi.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Activities.GameActivity;


public class ClassSelectionFragment extends Fragment {

    String flavor;

    public ClassSelectionFragment(){

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

        View rootView = inflater.inflate(R.layout.class_select_fragment, container, false);
        Button mer = rootView.findViewById(R.id.class_btn_mer);
        Button ass = rootView.findViewById(R.id.class_btn_ass);
        Button arc = rootView.findViewById(R.id.class_btn_arc);



        mer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("MER");
            }
        });
        ass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("ASS");
            }
        });
        arc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("ARC");
            }
        });

        return rootView;
    }

    private void startGame(String s){
        Intent intent = new Intent( getActivity(),GameActivity.class);
        intent.putExtra("CLASS", s);
        intent.putExtra("Flavor", flavor);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Flavor", flavor);
    }
}

