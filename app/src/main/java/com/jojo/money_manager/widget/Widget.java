package com.jojo.money_manager.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.jojo.money_manager.R;
import com.jojo.money_manager.activity.MainActivity;
import com.jojo.money_manager.dao.AccountDao;
import com.jojo.money_manager.pojo.Account;

public class Widget extends AppWidgetProvider {

    private String count;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        AccountDao accountDao = new AccountDao(context);
        Account lastAccount = accountDao.findLastAccount();
        count = String.valueOf(lastAccount.getBalance());

        remoteViews.setTextViewText(R.id.appwidget_text, count);

        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, configPendingIntent);

        for(int id : appWidgetIds){
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (intent.getExtras() != null) {
            count = extras.getString("COUNT");
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

