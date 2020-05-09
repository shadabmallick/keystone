package com.academy.keytone.Slider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.academy.keytone.R;
import com.academy.keytone.activity.HomeScreen;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;

import java.util.ArrayList;
import java.util.HashMap;

public class SlideFragment extends Fragment {

  private static final String ARG_SECTION_NUMBER = "section_number";
  GlobalClass globalClass;
  ImageView img1;
  ProgressDialog pd;
  ArrayList<HashMap<String,String>> list_names;
  Shared_Preference prefrence;
  private ArrayList newArrayList = null;


  @StringRes
  private static final int[] PAGE_TITLES =
      new int[] { R.string.string1, R.string.string2, R.string.string3 };
  @StringRes
  private static final int[] PAGE_IMAGE =
      new int[] {
          R.mipmap.homebanner, R.mipmap.homebanner, R.mipmap.homebanner
      };

  private SliderViewModel sliderViewModel;

  public static SlideFragment newInstance(int index) {
    SlideFragment fragment = new SlideFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_SECTION_NUMBER, index);
    fragment.setArguments(bundle);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    globalClass = (GlobalClass)getActivity().getApplicationContext();
    prefrence = new Shared_Preference(getActivity());
    prefrence.loadPrefrence();
    newArrayList = HomeScreen.list_names;
    pd = new ProgressDialog(getActivity());
    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    pd.setMessage("Loading...");
   // browseJob();
    sliderViewModel = ViewModelProviders.of(this).get(SliderViewModel.class);
    int index = 1;
    if (getArguments() != null) {
      index = getArguments().getInt(ARG_SECTION_NUMBER);
    }
    sliderViewModel.setIndex(index);
  }

  @Override
  public View onCreateView(
          @NonNull LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_main, container, false);
    final TextView textView = root.findViewById(R.id.section_label);
    final ImageView imageView = root.findViewById(R.id.imageView);
    img1 = root.findViewById(R.id.img1);
    textView.bringToFront();
    img1.bringToFront();
    sliderViewModel.getText().observe(this, new Observer<Integer>() {
      @Override public void onChanged(Integer index) {
        textView.setText(PAGE_TITLES[index]);

        imageView.setImageResource(PAGE_IMAGE[index]);
      }
    });
    return root;
  }



}