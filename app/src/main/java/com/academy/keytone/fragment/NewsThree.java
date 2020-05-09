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
import com.academy.keytone.adapter.AdapterExtra;
import com.academy.keytone.adapter.NewsAdapter;
import com.academy.keytone.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsThree extends Fragment {
    View view;
    TextView textView5;


    private List<News> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private AdapterExtra groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_lsyout, container, false);



     //   groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_news);
        //  textView5 = view.findViewById(R.id.textView5);



        groceryAdapter = new AdapterExtra(groceryList1, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);

        populategroceryList1();

        return view;
    }
    private void populategroceryList1(){
       // groceryList1.clear();
        News event1 = new News("Spacecraft Designer Hong Yang Makes Keytone Education Salon ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.newssample1,"Duration: 6hrs","2020/06/01");

        News event2 = new News("Spacecraft Designer Hong Yang Makes Keytone Education Salon ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.newssample2,"Duration: 6hrs","2020/06/01");
        News event3 = new News("Spacecraft Designer Hong Yang Makes Keytone Education Salon ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.newssample3,"Duration: 6hrs","2020/06/01");



        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryAdapter.notifyDataSetChanged();
    }
}