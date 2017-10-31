package com.example.mylibrary.tibbi.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylibrary.tibbi.Activities.GameActivity;
import com.example.mylibrary.tibbi.Adapters.CursorAdapter;
import com.example.mylibrary.tibbi.Data.DBContract;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Utils.MainCharacter;
import com.example.mylibrary.tibbi.Utils.Weapon;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class InventoryFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private CursorAdapter mCursorAdapter;
    private static final int TASK_LOADER_ID = 0;
    private MainCharacter mChar;
public InventoryFragment(){

}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent intent = getActivity().getIntent();
        mChar = intent.getParcelableExtra("char");
        getLoaderManager().initLoader(TASK_LOADER_ID, null, InventoryFragment.this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);
        ListView listView = rootView.findViewById(R.id.fragment_inventory_ListView);
        final TextView textView = rootView.findViewById(R.id.fragment_inventory_TextView);
        final FloatingActionButton check_btn = rootView.findViewById(R.id.fragment_inventory_fab);


        Cursor nCursor = null;
        mCursorAdapter = new CursorAdapter(getActivity(),nCursor, false);
        listView.setAdapter(mCursorAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                double[] stats = new double[7];
                String [] whereArgs = {mChar.getC_class()};
                Cursor c = getActivity().getContentResolver().query(DBContract.InventoryEntry.CONTENT_URI,null,"item_type=?",
                        whereArgs,null);
                assert c != null;
                c.moveToPosition(position);
                String name = c.getString(1);
                String type = c.getString(2);
                stats[0] = c.getDouble(3);
                stats[1] = c.getDouble(4);
                stats[2] = c.getDouble(5);
                stats[3] = c.getDouble(6);
                stats[4] = c.getDouble(7);
                stats[5] = c.getDouble(8);
                stats[6] = c.getDouble(9);

                Weapon mWeapon = new Weapon(name, type, stats);
                mChar.addWeapon(mWeapon);
                textView.setText(name);

                c.close();

            }
        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("char", (Parcelable) mChar);
                intent.putExtra("Flavor", mChar.getFlavor());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @SuppressLint("StaticFieldLeak")
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new android.content.AsyncTaskLoader<Cursor>(getActivity()) {

            Cursor mTaskData = null;

            @Override
            protected void onStartLoading(){

                if (mTaskData != null){
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                }else{
                    forceLoad();
                }
            }


            public Cursor loadInBackground() {

                String [] whereArgs = {mChar.getC_class()};
                try {
                    return getActivity().getContentResolver().query(DBContract.InventoryEntry.CONTENT_URI,
                            null,
                            "item_type=?",
                            whereArgs,
                            null);
                }catch (Exception e){
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data){
                mTaskData = data;
                super.deliverResult(data);
            }
        };


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
