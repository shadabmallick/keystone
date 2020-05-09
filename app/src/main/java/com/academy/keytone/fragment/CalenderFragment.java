package com.academy.keytone.fragment;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.academy.keytone.R;
import com.academy.keytone.adapter.calenderAdapter;
import com.academy.keytone.model.EventDetails;
import com.academy.keytone.model.MyEventDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderFragment extends Fragment {
    View view;
    TextView textView5;
    TextView tool_title;
    Toolbar toolbar;
    ImageView img_profile,back;
    TextView ll_bottom;
    RelativeLayout rel_tool;
    List<EventDay> events = new ArrayList<>();
    List<EventDay> events1 = new ArrayList<>();

    AlertDialog alertDialog;
    Calendar calendarSec  = Calendar.getInstance();
    Calendar calendarMenustration  = Calendar.getInstance();
    ArrayList<String> arrayList = new ArrayList<>();
    TextView add_menstruation, add_note, add_intimcy;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String noteType;
    Spinner spinner;
    MyEventDay myEventDay;
    CalendarView calendarView;
    List<EventDay> eventsAddImages = new ArrayList<>();
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    private List<EventDetails> eventDetails_new ;
    private RecyclerView groceryRecyclerView,recyclerView;
    private calenderAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Calendar calendar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calender_page, container, false);
        rel_tool=getActivity().findViewById(R.id.rel_tool);
        rel_tool.setVisibility(View.GONE);
        calendar = Calendar.getInstance();
        List<EventDay> events = new ArrayList<>();
        spinner=view.findViewById(R.id.spinner1);
        calendarView = view.findViewById(R.id.calendarView);
        eventDetails_new = new ArrayList<>();
        List<String> categories = new ArrayList<String>();
        categories.add("SCHOOL-WIDE");
       //  categories.add("Parent");
       //  categories.add("Teacher");
        events.add(new EventDay(calendar, R.drawable.circel));
        recyclerView = view.findViewById(R.id.recycler_cal);
        groceryAdapter = new calenderAdapter(eventDetails_new, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),  R.layout.text_two, R.id.txt1, categories);
        CalendarUtils.getDrawableText(getActivity(), "jfdbhf", Typeface.DEFAULT, R.color.text_color, 20);

        spinner.setAdapter(dataAdapter);
        populategroceryList1();





        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
       /* img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("AQI");*/
    }

    private void populategroceryList1(){

        Log.d("TAG", "populategroceryList1: ");
       // dialog.show();
        //  groceryList1.clear();
        EventDetails event1 = new EventDetails("","",
                "",R.mipmap.event,"","");

        EventDetails event2 = new EventDetails("","",
                "",R.mipmap.event,"","");
        EventDetails event3 = new EventDetails("","",
                "",R.mipmap.event,"","");

        eventDetails_new.add(event1);
        eventDetails_new.add(event2);
        eventDetails_new.add(event3);
        eventDetails_new.add(event1);
        eventDetails_new.add(event2);
        eventDetails_new.add(event3);
        groceryAdapter.notifyDataSetChanged();
       // dialog.hide();
    }

}