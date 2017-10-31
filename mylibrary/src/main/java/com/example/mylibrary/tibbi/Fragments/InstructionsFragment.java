package com.example.mylibrary.tibbi.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mylibrary.tibbi.Activities.GameActivity;
import com.example.mylibrary.tibbi.Activities.LibMainActivity;
import com.example.mylibrary.tibbi.R;


public class InstructionsFragment extends Fragment {

    public InstructionsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        Button button  = rootView.findViewById(R.id.inst_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LibMainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });


        return rootView;
    }
}
