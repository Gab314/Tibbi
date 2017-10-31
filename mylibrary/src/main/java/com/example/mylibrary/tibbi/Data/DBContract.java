package com.example.mylibrary.tibbi.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.mylibrary.tibbi.BuildConfig;

public class DBContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID +  ".provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY_DB = "InventoryDB";

    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INVENTORY_DB).build();


        public static final String TABLE_NAME = "InventoryDB";
        public static final String COLUMN_NAME = "item_name";
        public static final String COLUMN_TYPE = "item_type";
        //stats
        public static final String COLUMN_HP = "item_hp";
        public static final String COLUMN_WIL = "item_wil";
        public static final String COLUMN_AGI = "item_agi";
        public static final String COLUMN_DEX = "item_dex";
        public static final String COLUMN_DET = "item_det";
        public static final String COLUMN_REF = "item_ref";
        public static final String COLUMN_LUC = "item_luc";


    }
}