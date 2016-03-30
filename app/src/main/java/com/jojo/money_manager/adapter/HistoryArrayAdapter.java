package com.jojo.money_manager.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.pojo.History;

import java.util.List;

public class HistoryArrayAdapter extends ArrayAdapter<History>{
    public HistoryArrayAdapter(Context context, int resource, List<History> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View line = layoutInflater.inflate(R.layout.history_line, null);

        History history = getItem(position);

        TextView value = (TextView) line.findViewById(R.id.valueLine);
        TextView comment = (TextView) line.findViewById(R.id.commentLine);
        TextView date = (TextView) line.findViewById(R.id.dateLine);

        value.setText(history.getValue());
        comment.setText(history.getComment());
        date.setText(history.getDate());


        return line;
    }
}
