package com.academy.keytone.fragment;

import android.app.ProgressDialog;
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
import com.academy.keytone.adapter.AdapterExtra;
import com.academy.keytone.adapter.NewsAdapter;
import com.academy.keytone.model.News;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PublicationMain extends Fragment {
    View view;
    TextView tool_title;
   ImageView img_profile,back;

    private List<News> groceryList1 ;
    private RecyclerView groceryRecyclerView,recyclerView;
    private AdapterExtra groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog dialog;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.publication_main_layout, container, false);
        Log.d(TAG, "onCreateView: ");
        dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        tool_title = getActivity().findViewById(R.id.tool_title);

        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("PUBLICATIONS");
        //groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_publication);
        //textView5 = view.findViewById(R.id.textView5);
        groceryList1 = new ArrayList<>();

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

        groceryAdapter = new AdapterExtra(groceryList1, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groceryAdapter);

        populategroceryList1();


        return view;
    }
    private void populategroceryList1(){

        Log.d("TAG", "populategroceryList1: ");
        dialog.show();
        //  groceryList1.clear();
        News event1 = new News("Keystone Magazine 2020 summer\n"+" issue ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.publication1,"Duration: 6hrs","2020/06/01");

        News event2 = new News("Keystone Magazine 2020 summer\n"+" issue","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.public2,"Duration: 6hrs","2020/06/01");
        News event3 = new News("Keystone Magazine 2020 summer\n"+" issue  ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.public3,"Duration: 6hrs","2020/06/01");

        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryAdapter.notifyDataSetChanged();
        dialog.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("PUBLICATIONS");
    }
}