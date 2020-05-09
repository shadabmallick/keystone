package com.academy.keytone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.academy.keytone.R;
import com.google.android.material.tabs.TabLayout;

public class InTheLoopFragment extends Fragment {
    View view;
    TextView tool_title;
    Toolbar toolbar;
    ImageView img_profile,back;

    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.in_the_loop, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tool_title = getActivity().findViewById(R.id.tool_title);
        // img_profile  =toolbar.findViewById(R.id.img_profile);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("IN THE LOOP");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(),5);

        // Set up the ViewPager with the sections adapter.
        mViewPager =  view.findViewById(R.id.view_pager_loop);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
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


        return view;
    }
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


        public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragmentloop = null;
            Log.d("TAG", "getItem: "+position);

            switch (position) {

                case 0:
                    fragmentloop = new SubInTheLoop();
                    break;
                case 1:
                    fragmentloop = new SubInTheLoop();
                    break;
                case 2:
                    fragmentloop = new SubInTheLoop();
                    break;
                case 3:
                    fragmentloop = new SubInTheLoop();
                    break;
                case 4:
                    fragmentloop = new SubInTheLoop();
                    break;
                case 5:
                    fragmentloop = new SubInTheLoop();
                    break;



            }
            return fragmentloop;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.d("TAG", "getItem1: "+position);

            switch (position) {
                case 0:
                    return "Featured";
                case 1:
                    return "Academics";
                case 2:
                    return "Arts";
                case 3:
                    return "College";
                case 4:
                    return "Sports";
                case 5:
                    return "Student life";


                               }
            return null;
        }

    }

}
