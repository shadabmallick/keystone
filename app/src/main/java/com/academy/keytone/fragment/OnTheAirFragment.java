package com.academy.keytone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.academy.keytone.R;
import com.google.android.material.tabs.TabLayout;

public class OnTheAirFragment extends Fragment {
    View view;
    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.on_the_air, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(),5);

        // Set up the ViewPager with the sections adapter.
        mViewPager =  view.findViewById(R.id.view_pager_on_the_air);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_on_the_air);
        tabLayout.setupWithViewPager(mViewPager);



        return view;
    }
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


        public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragmentOnTheAir = null;
            Log.d("TAG", "getItem: "+position);

            switch (position) {

                case 0:
                    fragmentOnTheAir = new OnTheAirOne();
                    break;
                case 1:
                    fragmentOnTheAir = new OnTheAirOne();
                    break;
                case 2:
                    fragmentOnTheAir = new OnTheAirOne();
                    break;
                case 3:
                    fragmentOnTheAir = new OnTheAirOne();
                    break;
                case 4:
                    fragmentOnTheAir = new OnTheAirOne();
                    break;
                case 5:
                    fragmentOnTheAir = new OnTheAirOne();
                    break;



            }
            return fragmentOnTheAir;
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
