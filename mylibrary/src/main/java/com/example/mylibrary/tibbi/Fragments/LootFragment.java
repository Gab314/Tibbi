package com.example.mylibrary.tibbi.Fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Activities.GameActivity;
import com.example.mylibrary.tibbi.Data.DBContract;
import com.example.mylibrary.tibbi.Utils.MainCharacter;
import com.example.mylibrary.tibbi.Utils.Mobs;
import com.example.mylibrary.tibbi.Utils.Weapon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class LootFragment extends Fragment {

    MainCharacter mChar;
    ArrayList<Weapon> myWeaponList;
    Weapon weapon;
    int position;
    String wName;
    public LootFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Intent intent = getActivity().getIntent();
            mChar = intent.getParcelableExtra("CHAR");
            position = intent.getIntExtra("position", 0);
            myWeaponList = new ArrayList<>();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_loot, container, false);
        //Better use a list, nah
        final Button loot1_button = rootview.findViewById(R.id.loot_loot1);
        TextView loot = rootview.findViewById(R.id.fragment_loot_TextView);
        wName = "Weapon";
        if (savedInstanceState != null){
            position = savedInstanceState.getInt("position");
            mChar = savedInstanceState.getParcelable("char");
            myWeaponList = savedInstanceState.getParcelableArrayList("wList");
            wName = savedInstanceState.getString("wName");
            weapon = savedInstanceState.getParcelable("weapon");
        }
        if (savedInstanceState == null){
            FetchLootTask fetchLootTask = new FetchLootTask(loot);
            fetchLootTask.execute();
        }





        loot.setText(wName);

        loot1_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final ContentValues contentValues = new ContentValues();
                contentValues.put(DBContract.InventoryEntry.COLUMN_NAME, wName);
                contentValues.put(DBContract.InventoryEntry.COLUMN_TYPE, weapon.getType());
                contentValues.put(DBContract.InventoryEntry.COLUMN_HP, weapon.getHP());
                contentValues.put(DBContract.InventoryEntry.COLUMN_WIL, weapon.getWIL());
                contentValues.put(DBContract.InventoryEntry.COLUMN_AGI, weapon.getAGI());
                contentValues.put(DBContract.InventoryEntry.COLUMN_DEX, weapon.getDEX());
                contentValues.put(DBContract.InventoryEntry.COLUMN_DET, weapon.getDET());
                contentValues.put(DBContract.InventoryEntry.COLUMN_REF, weapon.getREF());
                contentValues.put(DBContract.InventoryEntry.COLUMN_LUC, weapon.getLUC());

                getActivity().getContentResolver().insert(DBContract.InventoryEntry.CONTENT_URI, contentValues);

                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("WEAPON", wName);
                intent.putExtra("char", (Parcelable) mChar);
                intent.putExtra("Flavor", mChar.getFlavor());
                startActivity(intent);
            }
        });

        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("position", position);
        savedInstanceState.putParcelable("char", mChar);
        savedInstanceState.putParcelableArrayList("wList", myWeaponList);
        savedInstanceState.putString("wName", wName);
        savedInstanceState.putParcelable("weapon", weapon);
    }

    private class FetchLootTask extends AsyncTask<Void,Void,ArrayList<Weapon>>{

        TextView textView;

        private FetchLootTask(TextView textView1){
            this.textView = textView1;
        }

        @Override
        protected ArrayList<Weapon> doInBackground(Void... voids) {
            String json;
            ArrayList<Weapon> wList = new ArrayList<>();
            try {
                InputStream is = getActivity().getAssets().open("weaponList.json");
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
                JSONObject mobJson = new JSONObject(json);

                final String KEY_NAME = "name";
                final String KEY_STATS = "stats";
                final String KEY_TYPE = "type";
                JSONArray classArray = mobJson.getJSONArray(mChar.getC_class());
                JSONArray lootLvlArray = classArray.getJSONArray(position);
                for (int i = 0; i < lootLvlArray.length(); i++) {
                    JSONObject object = lootLvlArray.getJSONObject(i);
                    String name = object.getString(KEY_NAME);
                    String type = object.getString(KEY_TYPE);
                    JSONArray stats = object.getJSONArray(KEY_STATS);
                    double[] statsArray = new double[stats.length()];
                    for (int j = 0; j < stats.length(); j++){
                        statsArray[j] = stats.getInt(j);
                    }

                    Weapon weapon = new Weapon(name,type, statsArray);

                    wList.add(weapon);
                    Log.v(getClass().getName(),"fetched");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return wList;
        }

        @Override
        protected void onPostExecute(ArrayList<Weapon> result) {
            myWeaponList.addAll(result);
            Random rdn = new Random();
            int r = rdn.nextInt(3);
            weapon = myWeaponList.get(r);
            wName = weapon.getName();
            textView.setText(wName);
        }
    }
}
