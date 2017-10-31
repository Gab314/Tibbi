package com.example.mylibrary.tibbi.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "inventory.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + DBContract.InventoryEntry.TABLE_NAME + " ( " +
                DBContract.InventoryEntry._ID + " INTEGER PRIMARY KEY, " +
                DBContract.InventoryEntry.COLUMN_NAME+ " TEXT NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_HP + " INTEGER NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_WIL + " INTEGER NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_AGI + " INTEGER NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_DEX + " INTEGER NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_DET + " INTEGER NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_REF + " INTEGER NOT NULL, " +
                DBContract.InventoryEntry.COLUMN_LUC + " INTEGER NOT NULL " +

                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.InventoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}