package com.jojo.money_manager.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
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
import com.jojo.money_manager.dao.AccountDao;
import com.jojo.money_manager.dao.HistoryDao;
import com.jojo.money_manager.pojo.Account;
import com.jojo.money_manager.pojo.History;

import java.text.DateFormat;
import java.util.Date;


public class CounterFragment extends Fragment {

    private static final boolean CREDIT = true;
    private static final boolean DEBIT = false;

    private View view;

    private Account account;
    private TextView balanceTextView;
    private TextView widgetTextView;
    private EditText balanceEditor;
    private EditText commentEditor;
    private EditText tagEditor;
    private AccountDao accountDao;
    private HistoryDao historyDao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_counter, container, false);

        accountDao = new AccountDao(getActivity());
        historyDao = new HistoryDao(getActivity());

        account = accountDao.findLastAccount();
        if(null == account){
            account = new Account(0);
        }

        Button credit = (Button) view.findViewById(R.id.credit);
        Button debit = (Button) view.findViewById(R.id.debit);


        widgetTextView = (TextView) view.findViewById(R.id.appwidget_text);

        balanceTextView = (TextView) view.findViewById(R.id.account);
        balanceTextView.setText(String.valueOf(account.getBalance()));

        balanceEditor = (EditText) view.findViewById(R.id.editValue);
        commentEditor = (EditText) view.findViewById(R.id.commentValue);
        tagEditor = (EditText) view.findViewById(R.id.tagValue);

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

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void debit() {
        String valueFromBalanceEditor = String.valueOf(balanceEditor.getText());
        if(isParsable(valueFromBalanceEditor)) {
            Float value = Float.valueOf(valueFromBalanceEditor);
            account.debit(value);
            balanceTextView.setText(String.valueOf(account.getBalance()));

           saveAccount(account, DEBIT);
            updateWidget(String.valueOf(account.getBalance()));
            resetEditor();
        }
    }

    private void credit() {
        String valueFromBalanceEditor = String.valueOf(balanceEditor.getText());
        if(isParsable(valueFromBalanceEditor)){
            Float value = Float.valueOf(valueFromBalanceEditor);
            account.credit(value);
            balanceTextView.setText(String.valueOf(account.getBalance()));

            saveAccount(account, CREDIT);
            updateWidget(String.valueOf(account.getBalance()));
            resetEditor();
        }
    }

    private void saveAccount(Account account, Boolean bool){
        saveAccount(account);
        saveHistory(bool);
    }

    private void saveAccount(Account account) {
        accountDao.insertAccount(account);
    }

    private void saveHistory(boolean bool){

        String symbol = bool ? "+" : "-";

        historyDao.insertHistory(new History(symbol + String.valueOf(balanceEditor.getText()),
                String.valueOf(commentEditor.getText()),
                extractDate(),
                String.valueOf(tagEditor.getText())));
    }

    private String extractDate() {
        Date today = new Date();

        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        return shortDateFormat.format(today);
    }

    private void resetEditor() {
        balanceEditor.setText("");
        commentEditor.setText("");
        tagEditor.setText("");
    }

    private boolean isParsable(String string){
        try{
            Float.parseFloat(string);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    private void updateWidget(String count){
        Intent intent = new Intent(getContext().getApplicationContext(), Widget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra("COUNT", count);

        int[] ids = AppWidgetManager.getInstance(getContext().getApplicationContext()).getAppWidgetIds(new ComponentName(getContext().getApplicationContext(), Widget.class));
        if(ids != null && ids.length > 0) {
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            getContext().getApplicationContext().sendBroadcast(intent);
        }
    }
}
