package com.jojo.money_manager.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.pojo.Detail;
import com.jojo.money_manager.pojo.History;

import java.util.List;

public class DetailArrayAdapter extends ArrayAdapter<Detail>{

    private Context context;

    public DetailArrayAdapter(Context context, int resource, List<Detail> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View line = layoutInflater.inflate(R.layout.detail_line, null);

        Detail detail = getItem(position);

        TextView value = (TextView) line.findViewById(R.id.detailValue);
        TextView tag = (TextView) line.findViewById(R.id.detailTag);

        value.setText(String.valueOf(detail.getCount()));
        tag.setText(detail.getTag());


        return line;
    }
}
