package com.academy.keytone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.adapter.HosAdapter;
import com.academy.keytone.model.News;

import java.util.ArrayList;
import java.util.List;

public class HosUpdateFragment extends Fragment {
    View view;
    TextView textView5;
    TextView tool_title,txt_one,txt_two,txt_three,txt_four,txt_five,txt_six,txt_seven;
    Toolbar toolbar;
    ImageView img_profile,back;
    private HosAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<News> groceryList1 = new ArrayList<>();
    RecyclerView recyler_view;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hos, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tool_title = getActivity().findViewById(R.id.tool_title);
        // img_profile  =toolbar.findViewById(R.id.img_profile);
        recyler_view=view.findViewById(R.id.recyler_view);
        txt_one=view.findViewById(R.id.txt_one);
        txt_two=view.findViewById(R.id.txt_two);
        txt_three=view.findViewById(R.id.txt_three);
        txt_four=view.findViewById(R.id.txt_four);
        txt_five=view.findViewById(R.id.txt_five);
        txt_six=view.findViewById(R.id.txt_six);
        txt_seven=view.findViewById(R.id.txt_seven);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("HOS CORNER");
        groceryAdapter = new HosAdapter(groceryList1, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyler_view.setLayoutManager(layoutManager);
        recyler_view.setAdapter(groceryAdapter);

           txt_one.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                 txt_one.setTextColor(getResources().getColor(R.color.red));
                 txt_two.setTextColor(getResources().getColor(R.color.dark_grey));
                 txt_three.setTextColor(getResources().getColor(R.color.dark_grey));
                 txt_four.setTextColor(getResources().getColor(R.color.dark_grey));
                 txt_five.setTextColor(getResources().getColor(R.color.dark_grey));
                  txt_six.setTextColor(getResources().getColor(R.color.dark_grey));
                 txt_seven.setTextColor(getResources().getColor(R.color.dark_grey));
               }
           });
           txt_two.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_one.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_two.setTextColor(getResources().getColor(R.color.red));
                   txt_three.setTextColor(getResources().getColor(R.color.dark_grey));
                  txt_four.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_five.setTextColor(getResources().getColor(R.color.dark_grey));
                  txt_six.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_seven.setTextColor(getResources().getColor(R.color.dark_grey));
               }
           });
           txt_three.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  txt_one.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_two.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_three.setTextColor(getResources().getColor(R.color.red));
                   txt_four.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_five.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_six.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_seven.setTextColor(getResources().getColor(R.color.dark_grey));
               }
           });
           txt_four.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    txt_one.setTextColor(getResources().getColor(R.color.dark_grey));
                    txt_two.setTextColor(getResources().getColor(R.color.dark_grey));
                    txt_three.setTextColor(getResources().getColor(R.color.dark_grey));
                    txt_four.setTextColor(getResources().getColor(R.color.red));
                    txt_five.setTextColor(getResources().getColor(R.color.dark_grey));
                    txt_six.setTextColor(getResources().getColor(R.color.dark_grey));
                     txt_seven.setTextColor(getResources().getColor(R.color.dark_grey));
               }
           });
           txt_five.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
               txt_one.setTextColor(getResources().getColor(R.color.dark_grey));
               txt_two.setTextColor(getResources().getColor(R.color.dark_grey));
                txt_three.setTextColor(getResources().getColor(R.color.dark_grey));
                txt_four.setTextColor(getResources().getColor(R.color.dark_grey));
               txt_five.setTextColor(getResources().getColor(R.color.red));
               txt_six.setTextColor(getResources().getColor(R.color.dark_grey));
                txt_seven.setTextColor(getResources().getColor(R.color.dark_grey));
               }
           });
           txt_six.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_one.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_two.setTextColor(getResources().getColor(R.color.dark_grey));
                       txt_three.setTextColor(getResources().getColor(R.color.dark_grey));
                       txt_four.setTextColor(getResources().getColor(R.color.dark_grey));
                       txt_five.setTextColor(getResources().getColor(R.color.dark_grey));
                       txt_six.setTextColor(getResources().getColor(R.color.red));
                       txt_seven.setTextColor(getResources().getColor(R.color.dark_grey));
               }
           });
           txt_seven.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                      txt_one.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_two.setTextColor(getResources().getColor(R.color.dark_grey));
                      txt_three.setTextColor(getResources().getColor(R.color.dark_grey));
                      txt_four.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_five.setTextColor(getResources().getColor(R.color.dark_grey));
                   txt_six.setTextColor(getResources().getColor(R.color.dark_grey));
                      txt_seven.setTextColor(getResources().getColor(R.color.red));
               }
           });

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
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("sdfbsdbfhdbfhbdhjh");

     //   groceryRecyclerView = view.findViewById(R.id.recycler1);

        populategroceryList1();


        return view;
    }
    private void populategroceryList1(){
        News event1 = new News("Dear parents" ,"2020/02/24",
                "Start: 10am@South Gate",R.mipmap.newssample1,"Last Friday I wrote about being oneself.There is so much more,of course" +
                "    ,that could be said about this topic.I want to add just one thought now.FOr three night at the end of last week audience in our Performing Arts Center were spellbound by a remarkable theater performance scripted and workshopped by students.Xiansheng was dedicated to,and commemorated,the woman","February");

        News event2 = new News("Dear parents","2020/02/24",
                "Start: 10am@South Gate",R.mipmap.newssample2,"Last Friday I wrote about being oneself.There is so much more,of course" +
                "    ,that could be said about this topic.I want to add just one thought now.FOr three night at the end of last week audience in our Performing Arts Center were spellbound by a remarkable theater performance scripted and workshopped by students.Xiansheng was dedicated to,and commemorated,the woman","January");
        News event3 = new News("Dear parents","2020/02/24",
                "Start: 10am@South Gate",R.mipmap.newssample3,"Last Friday I wrote about being oneself.There is so much more,of course"+
                "    ,that could be said about this topic.I want to add just one thought now.FOr three night at the end of last week audience in our Performing Arts Center were spellbound by a remarkable theater performance scripted and workshopped by students.Xiansheng was dedicated to,and commemorated,the woman","December");



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
        tool_title.setText("HOS CORNER");
    }
}