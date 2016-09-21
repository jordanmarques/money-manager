package com.jojo.money_manager.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.widget.Widget;
import com.jojo.money_manager.pojo.Account;
import com.jojo.money_manager.pojo.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class CounterFragment extends Fragment {

    private static final boolean CREDIT = true;
    private static final boolean DEBIT = false;

    private View view;

    private Account account;
    private TextView balanceTextView;
    private TextView widgetTextView;
    private EditText balanceEditor;
    private EditText commentEditor;
    private Realm realm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_counter, container, false);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getActivity().getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();

        account = realm.where(Account.class).findFirst();

        if(null == account){
            account = new Account(0l);
        }

        Button credit = (Button) view.findViewById(R.id.credit);
        Button debit = (Button) view.findViewById(R.id.debit);


        widgetTextView = (TextView) view.findViewById(R.id.appwidget_text);

        balanceTextView = (TextView) view.findViewById(R.id.account);
        balanceTextView.setText(String.valueOf(account.getBalance()));

        balanceEditor = (EditText) view.findViewById(R.id.editValue);
        commentEditor = (EditText) view.findViewById(R.id.commentValue);

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credit(String.valueOf(balanceEditor.getText()));
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debit(String.valueOf(balanceEditor.getText()));
            }
        });

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void debit(String valueFromBalanceEditor) {
        if(isParsable(valueFromBalanceEditor)) {
            final Long value = Long.valueOf(valueFromBalanceEditor);


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    account.debit(value);
                }
            });

            balanceTextView.setText(String.valueOf(account.getBalance()));

            saveAccount(account);
            saveHistory(DEBIT, valueFromBalanceEditor);

            updateWidget();
            resetEditor();
        }
    }

    private void credit(String valueFromBalanceEditor) {
        if(isParsable(valueFromBalanceEditor)){
           final Long value = Long.valueOf(valueFromBalanceEditor);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    account.credit(value);
                }
            });


            balanceTextView.setText(String.valueOf(account.getBalance()));

            saveAccount(account);
            saveHistory(CREDIT, valueFromBalanceEditor);

            updateWidget();
            resetEditor();
        }
    }

    private void saveAccount(Account account) {
        realm.beginTransaction();
        realm.copyToRealm(account);
        realm.commitTransaction();
    }

    private void saveHistory(boolean bool, String valueFromBalance){

        String symbol = bool ? "+" : "-";

        History history = new History(symbol + valueFromBalance,
                String.valueOf(commentEditor.getText()),
                extractDate());

        realm.beginTransaction();
        realm.copyToRealm(history);
        realm.commitTransaction();

    }

    private String extractDate() {
        Date today = new Date();

        DateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        RemoteViews remoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.widget);
        ComponentName thisWidget = new ComponentName(getActivity(), Widget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, String.valueOf(account.getBalance()));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
