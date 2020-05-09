package com.academy.keytone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.adapter.EventNewAdapter;
import com.academy.keytone.model.EventOne;

import java.util.ArrayList;
import java.util.List;

public class NewsEventOne extends Fragment {
    View view;
    TextView textView5;


    private List<EventOne> EventArray = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private EventNewAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RelativeLayout rel_tool;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.event_layout, container, false);



     //   groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_event);
        //  textView5 = view.findViewById(R.id.textView5);
        rel_tool=getActivity().findViewById(R.id.rel_tool);
        rel_tool.setVisibility(View.VISIBLE);


        groceryAdapter = new EventNewAdapter(EventArray, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);


        populategroceryList1();
        return view;
    }
    private void populategroceryList1(){
        EventArray.clear();
        EventOne event1 = new EventOne("Academics | Lorem lpsum is dummy text of the ","",
                "Start: 10am@South Gate",R.mipmap.sample1,"Keystone,Beijing","2020/06/01");

        EventOne event2 = new EventOne("Academics | Lorem lpsum is dummy text of the ","",
                "Start: 10am@South Gate",R.mipmap.sample2,"Keystone,Beijing","2020/06/01");
        EventOne event3 = new EventOne("Academics | Lorem lpsum is dummy text of the  "," ",
                "Start: 10am@South Gate",R.mipmap.sample1,"Keystone,Beijing","2020/06/01");



        EventArray.add(event1);
        EventArray.add(event2);
        EventArray.add(event3);
        EventArray.add(event1);
        EventArray.add(event2);
        EventArray.add(event3);
        groceryAdapter.notifyDataSetChanged();
    }
}