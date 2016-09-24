package com.jojo.money_manager.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.jojo.money_manager.R;
import com.jojo.money_manager.fragment.CounterFragment;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent configIntent = new Intent(context, CounterFragment.class);

        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, configPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

