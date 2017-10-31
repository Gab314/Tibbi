package com.example.mylibrary.tibbi.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.example.mylibrary.tibbi.Activities.GameActivity;
import com.example.mylibrary.tibbi.Activities.LibMainActivity;
import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Utils.MainCharacter;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Bundle option = appWidgetManager.getAppWidgetOptions(appWidgetId);
        CharSequence wHP,wWIL,wAGI,wDEX,wDET,wREF,wLUC;
        double[] doubles = option.getDoubleArray("char");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        if (doubles != null) {
            wHP = "HP: " + String.valueOf(doubles[0]);
            wWIL = "WIL: " + String.valueOf(doubles[1]);
            wAGI = "AGI: " + String.valueOf(doubles[2]);
            wDEX = "DEX: " + String.valueOf(doubles[3]);
            wDET = "DET: " + String.valueOf(doubles[4]);
            wREF = "REF: " + String.valueOf(doubles[5]);
            wLUC = "LUC: " + String.valueOf(doubles[6]);


            views.setTextViewText(R.id.widget_HP, wHP);
            views.setTextViewText(R.id.widget_WIL, wWIL);
            views.setTextViewText(R.id.widget_AGI, wAGI);
            views.setTextViewText(R.id.widget_DEX, wDEX);
            views.setTextViewText(R.id.widget_DET, wDET);
            views.setTextViewText(R.id.widget_REF, wREF);
            views.setTextViewText(R.id.widget_LUC, wLUC);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

