package com.example.mylibrary.tibbi.Fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Activities.FightActivity;
import com.example.mylibrary.tibbi.Adapters.RecyclerLadderAdapter;
import com.example.mylibrary.tibbi.Utils.MainCharacter;
import com.example.mylibrary.tibbi.Utils.Mobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class LadderFragment extends Fragment implements RecyclerLadderAdapter.ItemClickListener{
    RecyclerLadderAdapter adapter;
    ArrayList<Mobs> mobsArrayList;
    private MainCharacter mChar;
    public LadderFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){

            View rootView = inflater.inflate(R.layout.fragment_ladder, container, false);
            RecyclerView rv = rootView.findViewById(R.id.fragment_ladder_RecyclerView);
            mobsArrayList = new ArrayList<>();

            Intent intent = getActivity().getIntent();
            mChar = intent.getParcelableExtra("char");

            if(savedInstanceState != null){
                mobsArrayList = savedInstanceState.getParcelableArrayList("MOB_LIST");
                mChar = (MainCharacter) savedInstanceState.getSerializable("CHAR");
            }

            rv.setHasFixedSize(true);
            int numberOfColumns = 1;
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
            rv.setLayoutManager(mLayoutManager);

            adapter = new RecyclerLadderAdapter(getActivity(), mobsArrayList);

            rv.setAdapter(adapter);
            adapter.setClickListener(this);
            FetchMobsTask fetchMobsTask = new FetchMobsTask();
            fetchMobsTask.execute("GO");

            return rootView;
        }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("CHAR",mChar);
        savedInstanceState.putParcelableArrayList("MOB_LIST", mobsArrayList);

    }
    @Override
    public void onItemClick(View view, int position) {
        Mobs cpu = adapter.getItem(position);
        Bundle extra = new Bundle();
        Bundle stats = new Bundle();
        stats.putParcelable("STATS", mChar);
        extra.putParcelable("CPU", cpu);

        Intent intent = new Intent(getActivity(), FightActivity.class);
        intent.putExtra("CPU_BUNDLE", extra);
        intent.putExtra("STATS_BUNDLE",stats);
        intent.putExtra("position",position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }else startActivity(intent);
    }


    class FetchMobsTask extends AsyncTask<String,Void,ArrayList<Mobs>>{


        @Override
        protected ArrayList<Mobs> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            ArrayList<Mobs> myMobList = new ArrayList<>();
            final String KEY_NAME = "name";
            final String KEY_STATS = "stats";
            String json;
            try {
                InputStream is = getActivity().getAssets().open("mobList.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }

            try {
                JSONArray mobJson = new JSONArray(json);

                for (int i = 0; i < mobJson.length(); i++) {

                    JSONObject object = mobJson.getJSONObject(i);
                    String name = object.getString(KEY_NAME);
                    JSONArray stats = object.getJSONArray(KEY_STATS);
                    double[] statsArray = new double[stats.length()];
                        for (int j = 0; j < stats.length(); j++){
                            statsArray[j] = stats.getInt(j);
                        }

                    Mobs mobs = new Mobs(name,statsArray);

                    myMobList.add(mobs);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return myMobList;
        }

        @Override
        protected void onPostExecute(ArrayList<Mobs> result) {

            if (adapter != null) {
                adapter.clear();
            }

            mobsArrayList.addAll(result);
            adapter.notifyDataSetChanged();
        }
    }
}

