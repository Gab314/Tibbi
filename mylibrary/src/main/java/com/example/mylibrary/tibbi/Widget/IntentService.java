package com.example.mylibrary.tibbi.Widget;


import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Utils.MainCharacter;

import java.util.ArrayList;

public class IntentService extends android.app.IntentService{

    public static final String ACTION_PUT_ING = "com.example.gabriel.tibbi.action.insert";

    public static void startActionPutStats(Context context, MainCharacter mainCharacter){

        Intent intent = new Intent(context, IntentService.class);
        intent.setAction(ACTION_PUT_ING);
        intent.putExtra("char", (Parcelable) mainCharacter);
        context.startService(intent);
    }

    public IntentService() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_PUT_ING == action){
                final MainCharacter character = intent.getParcelableExtra("char");
                handleActionIngredients(character);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void handleActionIngredients(MainCharacter mChar){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, AppWidget.class));
        Bundle options = new Bundle();
        options.putDoubleArray("char", getArrayFromChar(mChar));
        if(appWidgetIds.length > 0 ) {
            for (int appWidgetId : appWidgetIds) {
                appWidgetManager.updateAppWidgetOptions(appWidgetId, options);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_HP);
            }
            AppWidget.updateWidget(this, appWidgetManager, appWidgetIds);
        }

    }

    public double[] getArrayFromChar(MainCharacter c){
        double[] doubles = new double[7];

        doubles[0] = c.getTotal_HP();
        doubles[1] = c.getTotal_WIL();
        doubles[2] = c.getTotal_AGI();
        doubles[3] = c.getTotal_DEX();
        doubles[4] = c.getTotal_DET();
        doubles[5] = c.getTotal_REF();
        doubles[6] = c.getTotal_LUC();
        return doubles;
    }
}
