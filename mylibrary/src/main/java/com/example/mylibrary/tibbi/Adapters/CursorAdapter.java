package com.example.mylibrary.tibbi.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mylibrary.tibbi.R;


public class CursorAdapter extends android.widget.CursorAdapter{

    public CursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.inv_card, viewGroup, false);

    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView = view.findViewById(R.id.inv_card_TextView);

        textView.setText(cursor.getString(1));

    }
}