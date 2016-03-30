package com.jojo.money_manager.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.dao.AccountDao;
import com.jojo.money_manager.dao.HistoryDao;
import com.jojo.money_manager.pojo.Account;
import com.jojo.money_manager.pojo.History;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final boolean CREDIT = true;
    private static final boolean DEBIT = false;

    private Account account;
    private TextView balanceTextView;
    private TextView widgetTextView;
    private EditText balanceEditor;
    private EditText commentEditor;
    private AccountDao accountDao;
    private HistoryDao historyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountDao = new AccountDao(this);
        historyDao = new HistoryDao(this);

        account = accountDao.findLastAccount();
        if(null == account){
            account = new Account(0);
        }

        widgetTextView = (TextView) findViewById(R.id.appwidget_text);

        balanceTextView = (TextView) findViewById(R.id.account);
        balanceTextView.setText(String.valueOf(account.getBalance()));

        balanceEditor = (EditText) findViewById(R.id.editValue);
        commentEditor = (EditText) findViewById(R.id.commentValue);

        Button credit = (Button) findViewById(R.id.credit);
        Button debit = (Button) findViewById(R.id.debit);
        Button history = (Button) findViewById(R.id.history);

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credit();
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void debit() {
        String valueFromBalanceEditor = String.valueOf(balanceEditor.getText());
        if(isParsable(valueFromBalanceEditor)) {
            Float value = Float.valueOf(valueFromBalanceEditor);
            account.debit(value);
            balanceTextView.setText(String.valueOf(account.getBalance()));

            saveAccount();
            saveHistory(DEBIT);
            updateWidget();
            resetEditor();
        }
    }

    private void credit() {
        String valueFromBalanceEditor = String.valueOf(balanceEditor.getText());
        if(isParsable(valueFromBalanceEditor)){
            Float value = Float.valueOf(valueFromBalanceEditor);
            account.credit(value);
            balanceTextView.setText(String.valueOf(account.getBalance()));

            saveAccount();
            saveHistory(CREDIT);
            updateWidget();
            resetEditor();
        }
    }

    private void saveAccount() {
        accountDao.insertAccount(account);
    }

    private void saveHistory(boolean bool){

        String symbol = bool ? "+" : "-";

        historyDao.insertHistory(new History(symbol + String.valueOf(balanceEditor.getText()),
                String.valueOf(commentEditor.getText()),
                extractDate()));
    }

    private String extractDate() {
        Date today = new Date();

        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        return shortDateFormat.format(today);
    }

    private void resetEditor() {
        balanceEditor.setText("");
        commentEditor.setText("");
    }

    private boolean isParsable(String string){
        try{
            Float.parseFloat(string);
            return true;
        } catch(Exception e){
            return false;
        }
    }


    private void updateWidget(){
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        ComponentName thisWidget = new ComponentName(context, Widget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, String.valueOf(account.getBalance()));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
