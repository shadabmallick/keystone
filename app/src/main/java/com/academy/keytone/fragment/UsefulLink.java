package com.academy.keytone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.adapter.UsefullinkAdapter;
import com.academy.keytone.model.News;

import java.util.ArrayList;
import java.util.List;

public class UsefulLink extends Fragment {
    View view;
    TextView textView5;

    TextView tool_title;
    ImageView img_profile,back;
    private List<News> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private UsefullinkAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.useful_link, container, false);

        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        tool_title = getActivity().findViewById(R.id.tool_title);

        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("USEFUL LINKS");

     //   groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_usefullink);
        //  textView5 = view.findViewById(R.id.textView5);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    // super.onBackPressed();
                }
            }

        });


        groceryAdapter = new UsefullinkAdapter(groceryList1, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);

        populategroceryList1();

        return view;
    }
    private void populategroceryList1(){
       // groceryList1.clear();
        News event1 = new News("","",
                "",R.mipmap.p,"","");

        News event2 = new News("","",
                "",R.mipmap.p,"","");

        News event3 = new News("","",
                "",R.mipmap.p,"","");





        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("USEFUL LINKS");
    }
}