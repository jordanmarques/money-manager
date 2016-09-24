package com.jojo.money_manager.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.adapter.HistoryArrayAdapter;
import com.jojo.money_manager.dao.HistoryDao;
import com.jojo.money_manager.pojo.History;

import java.util.Collections;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryDao historyDao;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        historyDao = new HistoryDao(getActivity());
        refreshHistory();

        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            refreshHistory();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void refreshHistory() {
        List<History> historyList = historyDao.findAll();

        Collections.reverse(historyList);

        ListView listView = (ListView) view.findViewById(R.id.listView);

        HistoryArrayAdapter historyArrayAdapter = new HistoryArrayAdapter(getActivity(), R.layout.history_line, historyList);
        listView.setAdapter(historyArrayAdapter);
    }
}
