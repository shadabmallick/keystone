package com.academy.keytone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.adapter.InLoopAdapter;
import com.academy.keytone.model.NewsNew;

import java.util.ArrayList;
import java.util.List;

public class SubInTheLoop extends Fragment {
    View view;
    TextView textView5;


    private List<NewsNew> intheloop = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private InLoopAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.intheloop, container, false);



     //   groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_loop);
        //  textView5 = view.findViewById(R.id.textView5);



        groceryAdapter = new InLoopAdapter(intheloop, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);


        populategroceryList1();
        return view;
    }
    private void populategroceryList1(){
        intheloop.clear();
        NewsNew event1 = new NewsNew("Handle Together:Stories of Service from the Keystone Community ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.loop1,"Duration: 6hrs","2020/06/01");

        NewsNew event2 = new NewsNew("Turn Over a New Leaf:Keystone Faculty and Staff Share Their","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.loop2,"Duration: 6hrs","2020/06/01");
        NewsNew event3 = new NewsNew("Rise Together:Keystone Responds to Coronavirus Outbreak as One","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.loop1,"Duration: 6hrs","2020/06/01");



        intheloop.add(event1);
        intheloop.add(event2);
        intheloop.add(event3);
        intheloop.add(event1);
        intheloop.add(event2);
        intheloop.add(event3);
        groceryAdapter.notifyDataSetChanged();
    }
}