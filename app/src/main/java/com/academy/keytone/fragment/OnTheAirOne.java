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
import com.academy.keytone.adapter.OnTheAirAdapter;
import com.academy.keytone.model.OnTheAirModel;

import java.util.ArrayList;
import java.util.List;

public class OnTheAirOne extends Fragment {
    View view;
    TextView textView5;
    ImageView img_profile,back;
    TextView tool_title;

    private List<OnTheAirModel> OnTheAir = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private OnTheAirAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.on_the_air_layout, container, false);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        tool_title = getActivity().findViewById(R.id.tool_title);

        back  =getActivity().findViewById(R.id.back);
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("ON THE AIR");

     //   groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_on_the_air);
        //  textView5 = view.findViewById(R.id.textView5);



        groceryAdapter = new OnTheAirAdapter(OnTheAir, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);

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

        populategroceryList1();
        return view;
    }
    private void populategroceryList1(){
        OnTheAir.clear();
        OnTheAirModel event1 = new OnTheAirModel("Academics | Lorem lpsum is dummy text of the ","",
                "Start: 10am@South Gate",R.mipmap.on_the_air_one,"Keystone,Beijing","2020/06/01");

        OnTheAirModel event2 = new OnTheAirModel("Academics | Lorem lpsum is dummy text of the ","",
                "Start: 10am@South Gate",R.mipmap.on_the_air_two,"Keystone,Beijing","2020/06/01");
        OnTheAirModel event3 = new OnTheAirModel("Academics | Lorem lpsum is dummy text of the  "," ",
                "Start: 10am@South Gate",R.mipmap.on_the_air_one,"Keystone,Beijing","2020/06/01");



        OnTheAir.add(event1);
        OnTheAir.add(event2);
        OnTheAir.add(event3);
        OnTheAir.add(event1);
        OnTheAir.add(event2);
        OnTheAir.add(event3);
        groceryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("ON THE AIR");
    }
}