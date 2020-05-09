package com.academy.keytone.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.adapter.SingleEventAdapter;
import com.academy.keytone.model.EventDetails;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EventsDetailsHome extends Fragment {
    View view;
    TextView textView5;


    private List<EventDetails> eventDetails ;
    private RecyclerView groceryRecyclerView,recyclerView;
    private SingleEventAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog dialog;
    ImageView back;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
     RelativeLayout rel_tool;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.events_details, container, false);
        Log.d(TAG, "onCreateView: ");
         dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        rel_tool=getActivity().findViewById(R.id.rel_tool);
        rel_tool.setVisibility(View.GONE);
       //groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycerl_gallery);
        back = view.findViewById(R.id.back);
        //textView5 = view.findViewById(R.id.textView5);
        eventDetails = new ArrayList<>();

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


        groceryAdapter = new SingleEventAdapter(eventDetails, getActivity());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(groceryAdapter);

        populategroceryList1();


        return view;
    }
    private void populategroceryList1(){

        Log.d("TAG", "populategroceryList1: ");
        dialog.show();
      //  groceryList1.clear();
        EventDetails event1 = new EventDetails("","",
                "",R.mipmap.event,"","");

        EventDetails event2 = new EventDetails("","",
                "",R.mipmap.event,"","");
        EventDetails event3 = new EventDetails("","",
                "",R.mipmap.event,"","");

        eventDetails.add(event1);
        eventDetails.add(event2);
        eventDetails.add(event3);
        eventDetails.add(event1);
        eventDetails.add(event2);
        eventDetails.add(event3);
        groceryAdapter.notifyDataSetChanged();
        dialog.hide();
    }
}
