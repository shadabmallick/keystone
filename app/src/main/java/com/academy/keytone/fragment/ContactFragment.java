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

import com.academy.keytone.R;

public class ContactFragment extends Fragment {
    View view;
    TextView textView5;
    TextView tool_title;
    Toolbar toolbar;
    ImageView img_profile,back;



    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_us, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tool_title = getActivity().findViewById(R.id.tool_title);
        // img_profile  =toolbar.findViewById(R.id.img_profile);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("CONNECT US");

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




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("CONNECT US");
    }
}