package com.academy.keytone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.academy.keytone.R;
import com.academy.keytone.activity.ChangePassword;
import com.academy.keytone.activity.Login;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;

public class SettingFragment extends Fragment {
    View view;
    TextView tool_title;
     Toolbar toolbar;
     ImageView img_profile,back;
     RelativeLayout rl_logout,rl_mess;
    LinearLayout ll_profile,ll_setting;
    GlobalClass globalClass;
    Shared_Preference preference;


    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tool_title = getActivity().findViewById(R.id.tool_title);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        ll_setting  =getActivity().findViewById(R.id.ll_setting);
        rl_logout=view.findViewById(R.id.rl_logout);
        rl_mess=view.findViewById(R.id.rl_mess);
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
     //   groceryRecyclerView = view.findViewById(R.id.recycler1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                switchToFragment7();
            }

        });
        rl_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(), ChangePassword.class);
               startActivity(intent);
            }

        });
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                preference.clearPrefrence();
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                startActivity(intent);
            }
        });



        return view;
    }
    public void switchToFragment7() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        tool_title.setText(getResources().getString(R.string.settings));
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        ll_setting.setVisibility(View.GONE);
    }
}