package com.jojo.money_manager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.jojo.money_manager.R;
import com.jojo.money_manager.adapter.DetailArrayAdapter;
import com.jojo.money_manager.dao.HistoryDao;
import com.jojo.money_manager.pojo.Detail;
import com.jojo.money_manager.pojo.History;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailFragment extends Fragment {

    private HistoryDao historyDao;
    private View view;
    DatePicker datePickerTo;
    DatePicker datePickerFrom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        datePickerTo = (DatePicker) view.findViewById(R.id.datePickerTo);
        datePickerFrom = (DatePicker) view.findViewById(R.id.datePickerFrom);
        Button filterBtn = (Button) view.findViewById(R.id.filterBtn);

        historyDao = new HistoryDao(getActivity());

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void filter() {
        List<History> historyList = historyDao.findAll();

        Date fromDatePicker = getDateFromDatePicker(datePickerFrom);
        Date toDatePicker = getDateFromDatePicker(datePickerTo);

        Map<String, BigDecimal> totalMap = groupTagsByDate(fromDatePicker, toDatePicker, historyList);
        List<Detail> details = convertMapToDetailList(totalMap);


        ListView listView = (ListView) view.findViewById(R.id.listViewDetail);

        DetailArrayAdapter detailArrayAdapter = new DetailArrayAdapter(getActivity(), R.layout.detail_line, details);
        listView.setAdapter(detailArrayAdapter);
    }

    private List<Detail> convertMapToDetailList(Map<String, BigDecimal> totalMap) {
        List<Detail> result = new ArrayList<>();
        for(Map.Entry<String, BigDecimal> entry : totalMap.entrySet()){
            result.add(new Detail(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    private Map<String, BigDecimal> groupTagsByDate(Date fromDatePicker, Date toDatePicker, List<History> historyList) {
        Map<String, BigDecimal> result = new HashMap<>();
        for(History history : historyList){
            Date dateFromHistoryItem = getDateFromHistoryItem(history);

            if(!history.getTag().equals("")){
                if((dateFromHistoryItem.after(fromDatePicker) || DateUtils.isSameDay(dateFromHistoryItem, fromDatePicker)) &&
                        (dateFromHistoryItem.before(toDatePicker) || DateUtils.isSameDay(dateFromHistoryItem, toDatePicker))) {

                    if (result.containsKey(history.getTag())) {
                        result.put(history.getTag(), result.get(history.getTag()).add(new BigDecimal(history.getValue())));
                    } else {
                        result.put(history.getTag(), new BigDecimal(history.getValue()));
                    }
                }
            }
        }

        return result;
    }

    private Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private Date getDateFromHistoryItem(History history){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:m a", Locale.ENGLISH);

        try {
            return format.parse(history.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(history.getDate() + "unparsable");
        }

    }
}
