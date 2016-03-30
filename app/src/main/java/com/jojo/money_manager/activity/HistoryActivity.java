package com.jojo.money_manager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.adapter.HistoryArrayAdapter;
import com.jojo.money_manager.dao.HistoryDao;
import com.jojo.money_manager.pojo.History;

import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private HistoryDao historyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyDao = new HistoryDao(this);
        List<History> historyList = historyDao.findAll();

        Collections.reverse(historyList);

        ListView listView = (ListView) findViewById(R.id.listView);

        HistoryArrayAdapter historyArrayAdapter = new HistoryArrayAdapter(this, R.layout.history_line, historyList);
        listView.setAdapter(historyArrayAdapter);


    }
}
