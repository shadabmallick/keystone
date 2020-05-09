package com.academy.keytone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.academy.keytone.R;
import com.academy.keytone.activity.ChangePassword;
import com.academy.keytone.activity.HomeScreen;
import com.academy.keytone.activity.Login;
import com.academy.keytone.networkService.AppConfig;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.academy.keytone.networkService.AppConfig.login;
import static com.academy.keytone.networkService.AppConfig.news_list;

public class NewsFragment extends Fragment {
    String TAG="NewFragment";
    GlobalClass globalClass;
    Shared_Preference preference;
    View view;
    ArrayList<HashMap<String,String>> Array_news_list;
    ArrayList<HashMap<String,String>> Array_category;
    public SectionsPagerAdapter mSectionsPagerAdapter;
    String features,academic;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_layout, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(),5);
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        Array_news_list = new ArrayList<>();
        Array_category = new  ArrayList<>();
        NewsList();
//        Log.d("TAG", "getItem: "+Array_category.get(0).get("name"));

        // Set up the ViewPager with the sections adapter.
        mViewPager =  view.findViewById(R.id.view_pager_new);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);



        return view;
    }
    private void NewsList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+news_list, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());



                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");
                    String  news_image_url = jobj.get("news_image_url").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {
                        JsonArray categories=jobj.getAsJsonArray("categories");
                        for (int j = 0; j < categories.size(); j++) {
                            JsonObject images1 = categories.get(j).getAsJsonObject();

                            String product_sub_id = images1.get("id").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            JsonArray news_list=images1.getAsJsonArray("news_list");
                            for (int i = 0; i < news_list.size();i++) {
                                JsonObject list = news_list.get(i).getAsJsonObject();

                                String sub_id = list.get("id").toString().replaceAll("\"", "");
                                String category_id = list.get("category_id").toString().replaceAll("\"", "");
                                String image = list.get("image").toString().replaceAll("\"", "");
                                String title = list.get("title").toString().replaceAll("\"", "");
                                String title_cn = list.get("title_cn").toString().replaceAll("\"", "");
                                String description = list.get("description").toString().replaceAll("\"", "");
                                String description_cn = list.get("description_cn").toString().replaceAll("\"", "");
                                String slug = list.get("slug").toString().replaceAll("\"", "");
                                String group_ids = list.get("group_ids").toString().replaceAll("\"", "");
                                String datetime = list.get("datetime").toString().replaceAll("\"", "");
                                String addedOn = list.get("addedOn").toString().replaceAll("\"", "");
                                String modifiedOn = list.get("modifiedOn").toString().replaceAll("\"", "");
                                String status1 = list.get("status").toString().replaceAll("\"", "");
                                String deleted = list.get("deleted").toString().replaceAll("\"", "");
                                String addedBy = list.get("addedBy").toString().replaceAll("\"", "");
                                String modifiedBy = list.get("modifiedBy").toString().replaceAll("\"", "");
                                HashMap<String, String> hashMap = new HashMap<>();

                                hashMap.put("sub_id",sub_id);
                                hashMap.put("category_id",category_id);
                                hashMap.put("image",image);
                                hashMap.put("title",title);
                                hashMap.put("title_cn",title_cn);
                                hashMap.put("description",description);
                                hashMap.put("description_cn",description_cn);
                                hashMap.put("slug",slug);
                                hashMap.put("group_ids",group_ids);
                                hashMap.put("addedOn",addedOn);
                                hashMap.put("datetime",datetime);
                                hashMap.put("modifiedOn",modifiedOn);
                                hashMap.put("status",status1);
                                hashMap.put("deleted",deleted);
                                hashMap.put("addedBy",addedBy);
                                hashMap.put("modifiedBy",modifiedBy);
                                Array_news_list.add(hashMap);
                                Log.d(TAG, "Hashmap1 " + hashMap);
                            }
                            HashMap<String, String> Category = new HashMap<>();

                            Category.put("product_sub_id",product_sub_id);
                            Category.put("name",name);
                            Array_category.add(Category);


                        }
                        features=Array_category.get(0).get("name");
                        academic=Array_category.get(1).get("name");
                        Log.d("TAG", "getItem: "+Array_category.get(0).get("name"));
                        Log.d("TAG", "getItem: "+Array_category.get(1).get("name"));

                    }
                    else {

                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(getActivity(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Connection Error", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", globalClass.getId());


                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {



        public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragmentNews = null;

            switch (position) {

                case 0:
                    fragmentNews = new NewsOne();
                    break;
                case 1:
                    fragmentNews = new NewTwo();
                    break;
                case 2:
                    fragmentNews = new NewsThree();
                    break;
                case 3:
                    fragmentNews = new NewsThree();
                    break;
                case 4:
                    fragmentNews = new NewsThree();
                    break;
                case 5:
                    fragmentNews = new NewsThree();
                    break;



            }
            return fragmentNews;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {


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
