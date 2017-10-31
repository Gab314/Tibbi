package com.example.mylibrary.tibbi.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.appolica.flubber.Flubber;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Utils.MainCharacter;
import com.example.mylibrary.tibbi.Utils.Mobs;
import com.example.mylibrary.tibbi.Utils.NewPvEGame;


public class FightFragment extends Fragment {

    private MainCharacter mChar1;
    private Mobs cpu;
    private NewPvEGame currentGame;


    public FightFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fight, container, false);
        Intent intent = getActivity().getIntent();


            cpu =  intent.getBundleExtra("CPU_BUNDLE").getParcelable("CPU");
            mChar1 = intent.getBundleExtra("STATS_BUNDLE").getParcelable("STATS");


        //char name
        TextView char_name = rootView.findViewById(R.id.fragment_fight_TextView_Char_Name);
        //text outcome
        final TextView text_outcome = rootView.findViewById(R.id.fragment_fight_TextView_Round_Outcome);
        //char stats
        final TextView char_HP = rootView.findViewById(R.id.fragment_fight_TextView_Char_HP);
        final ProgressBar char_HP_BAR = rootView.findViewById(R.id.progressBar_char);
        //mob stats
        final TextView mob_HP = rootView.findViewById(R.id.fragment_fight_TextView_Mob_HP);
        final ProgressBar mob_HP_BAR = rootView.findViewById(R.id.progressBar_mob);
        //mob MoveBar
        final LinearLayout heavy_bar = rootView.findViewById(R.id.fragment_fight_MoveBar_Heavy);
        final LinearLayout fast_bar = rootView.findViewById(R.id.fragment_fight_MoveBar_Fast);
        final LinearLayout throw_bar = rootView.findViewById(R.id.fragment_fight_MoveBar_Throw);
        //Buttons
        Button heavy = rootView.findViewById(R.id.fragment_fight_btn_heavy);
        Button fast = rootView.findViewById(R.id.fragment_fight_btn_fast);
        Button th = rootView.findViewById(R.id.fragment_fight_btn_throw);

        if (savedInstanceState != null) {

            mChar1 = (MainCharacter) savedInstanceState.getSerializable("SAVED_CHAR");
            cpu = (Mobs) savedInstanceState.getSerializable("SAVED_CPU");

        }
        //getters
        final double c_hp = mChar1.getTotal_HP();
        final double m_hp = cpu.getTotal_HP();

        char_name.setText(mChar1.getName());


        //setting initial stats to UI
        char_HP.setText(String.valueOf(c_hp));
        mob_HP.setText(String.valueOf(m_hp));
        char_HP_BAR.setMax((int) mChar1.getTotal_HP());
        mob_HP_BAR.setMax((int) cpu.getTotal_HP());
        if (savedInstanceState == null) {
            currentGame = new NewPvEGame(mChar1, cpu);
        }else currentGame = savedInstanceState.getParcelable("game");

        heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] outcome;
                 outcome = currentGame.newRound(0);
                if (outcome[2] == "W"){
                    updateHP(mob_HP,cpu.getTotal_HP(), mob_HP_BAR);
                }
                if (outcome[2] == "L") {
                    updateHP(char_HP ,mChar1.getTotal_HP(), char_HP_BAR);
                }
                updateSeqBar(heavy_bar, fast_bar, throw_bar);
                text_outcome.setText(outcome[0]);
                text_outcome.setVisibility(View.VISIBLE);
                setTextInvisible(text_outcome);
                if (outcome[1] ==  "VICTORY!" || outcome[1] == "DEFEAT!"){
                    finishFight(outcome[1]);
                }
            }
        });
        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] outcome;

                outcome = currentGame.newRound(1);
                if (outcome[2] == "W"){
                    updateHP(mob_HP,cpu.getTotal_HP(), mob_HP_BAR);
                }
                if (outcome[2] == "L") {
                    updateHP(char_HP ,mChar1.getTotal_HP(), char_HP_BAR);
                }
                updateSeqBar(heavy_bar, fast_bar, throw_bar);
                text_outcome.setText(outcome[0]);
                text_outcome.setVisibility(View.VISIBLE);
                setTextInvisible(text_outcome);
                if (outcome[1] ==  "VICTORY!" || outcome[1] == "DEFEAT!"){
                    finishFight(outcome[1]);
                }
            }
        });
        th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] outcome;

                outcome = currentGame.newRound(2);
                if (outcome[2] == "W"){
                    updateHP(mob_HP,cpu.getTotal_HP(), mob_HP_BAR);
                }
                if (outcome[2] == "L") {
                    updateHP(char_HP ,mChar1.getTotal_HP(), char_HP_BAR);
                }
                updateSeqBar(heavy_bar, fast_bar, throw_bar);
                text_outcome.setText(outcome[0]);
                text_outcome.setVisibility(View.VISIBLE);
                setTextInvisible(text_outcome);
                if (outcome[1] ==  "VICTORY!" || outcome[1] == "DEFEAT!"){
                    finishFight(outcome[1]);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable("SAVED_CHAR", mChar1);
        savedInstanceState.putSerializable("SAVED_CPU", cpu);
        savedInstanceState.putParcelable("game", currentGame);
    }

    public void setTextInvisible(View textView) {
        int delayMillis = 1000;
        Handler handler = new Handler();
        final View v = textView; // your view
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                TranslateAnimation animate = new TranslateAnimation(0, - 5 *v.getWidth(), 0, 0);
                animate.setDuration(300);
                v.startAnimation(animate);
                v.setVisibility(View.GONE);

            }
        }, delayMillis);

    }

    public void finishFight(String result){
        FightResultFragment fragment = new FightResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("RESULT", result);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_fight_RelativeLayout, fragment);
        fragmentTransaction.commit();
    }

    public void updateHP( TextView text, double HP, ProgressBar pBar){

        text.setText(String.valueOf(HP));
        //Animation
        Flubber.with()
                .animation(Flubber.AnimationPreset.POP)
                .interpolator(Flubber.Curve.BZR_EASE_IN)
                .duration(1000)
                .autoStart(true)
                .createFor(text);

        pBar.setProgress((int) HP);
    }
    public void updateSeqBar(LinearLayout he, LinearLayout fa, LinearLayout th){
        double[] mods = currentGame.getWeights();
        if (he != null) {
            he.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) mods[1]));
            fa.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) mods[2]));
            th.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) mods[0]));

        }
    }

}