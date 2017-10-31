package com.example.mylibrary.tibbi.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;




public class DBProvider extends ContentProvider {

    public static final int INVENTORY_DB = 100;
    public static final int INVENTORY_DB_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DBContract.CONTENT_AUTHORITY;
        //Directory for Favorite Movies DB
        matcher.addURI(authority,DBContract.PATH_INVENTORY_DB, INVENTORY_DB);
        //single item
        matcher.addURI(authority, DBContract.PATH_INVENTORY_DB + "/#", INVENTORY_DB_WITH_ID);

        return matcher;
    }

    private DBHelper mDBHelper;


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDBHelper = new DBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db = mDBHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {

            case INVENTORY_DB:{
                retCursor = db.query(DBContract.InventoryEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            }

            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return retCursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case INVENTORY_DB:
                return "vnd.android.cursor.dir" + "/" + DBContract.CONTENT_AUTHORITY + "/" + DBContract.PATH_INVENTORY_DB;

            case INVENTORY_DB_WITH_ID:
                return "vnd.android.cursor.item" + "/" + DBContract.CONTENT_AUTHORITY + "/" + DBContract.PATH_INVENTORY_DB;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case INVENTORY_DB:{
                long id = db.insert(DBContract.InventoryEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(DBContract.InventoryEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int tasksDeleted;
        if ( null == s) s = "1";
        switch (match) {

            case INVENTORY_DB_WITH_ID:{
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(DBContract.InventoryEntry.TABLE_NAME, "item_id=?", new String[]{id});
                break;
            }
            case INVENTORY_DB:{
                tasksDeleted = db.delete(DBContract.InventoryEntry.TABLE_NAME,null,null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted !=0) {getContext().getContentResolver().notifyChange(uri, null);}


        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowUpdated;
        String id = uri.getPathSegments().get(1);
        switch (match){
            case INVENTORY_DB_WITH_ID:{
                rowUpdated = db.update(DBContract.InventoryEntry.TABLE_NAME, contentValues, "_id=?", new String[]{id});
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        if (rowUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
