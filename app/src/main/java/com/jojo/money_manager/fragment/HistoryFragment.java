package com.jojo.money_manager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.adapter.HistoryArrayAdapter;
import com.jojo.money_manager.pojo.History;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoryFragment extends Fragment {

    private View view;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_history, container, false);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getActivity().getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();

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

        RealmResults<History> historyList = realm.where(History.class)
                .findAllSorted("date", Sort.DESCENDING);

        ListView listView = (ListView) view.findViewById(R.id.listView);

        HistoryArrayAdapter historyArrayAdapter = new HistoryArrayAdapter(getActivity(), R.layout.history_line, historyList);
        listView.setAdapter(historyArrayAdapter);
    }
}
