package com.academy.keytone.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.activity.HomeScreen;
import com.academy.keytone.adapter.NewsAdapter;
import com.academy.keytone.model.News;
import com.academy.keytone.networkService.AppConfig;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.academy.keytone.networkService.AppConfig.news_list;

public class NewsOne extends Fragment {
    View view;
    TextView textView5;
    GlobalClass globalClass;
    Shared_Preference preference;

    ArrayList<HashMap<String,String>> Array_news_list;
    ArrayList<HashMap<String,String>> Array_category;

    private List<News> groceryList1 ;
    private RecyclerView groceryRecyclerView,recyclerView;
    private NewsAdapter groceryAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog dialog;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
   RelativeLayout rel_tool;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_lsyout, container, false);
        Log.d(TAG, "onCreateView: ");
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
       //  dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        rel_tool=getActivity().findViewById(R.id.rel_tool);
        rel_tool.setVisibility(View.VISIBLE);
       //groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler_news);
        //textView5 = view.findViewById(R.id.textView5);
        groceryList1 = new ArrayList<>();
       Array_news_list = new ArrayList<>();
       Array_category = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



       // populategroceryList1();
        NewsList();


        return view;
    }
    private void populategroceryList1(){

        Log.d("TAG", "populategroceryList1: ");
        dialog.show();
      //  groceryList1.clear();
        News event1 = new News("Spacecraft Designer Hong Yang Makes Keytone Education Salon ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.newssample1,"Duration: 6hrs","2020/06/01");

        News event2 = new News("Spacecraft Designer Hong Yang Makes Keytone Education Salon ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.newssample2,"Duration: 6hrs","2020/06/01");
        News event3 = new News("Spacecraft Designer Hong Yang Makes Keytone Education Salon ","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard ",
                "Start: 10am@South Gate",R.mipmap.newssample3,"Duration: 6hrs","2020/06/01");

        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryAdapter.notifyDataSetChanged();
        dialog.hide();
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
                                String newText=(Html.fromHtml(description)).toString();
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
                                hashMap.put("image",news_image_url+image);
                                hashMap.put("title",title);
                                hashMap.put("title_cn",title_cn);
                                hashMap.put("description",newText);
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
                        groceryAdapter = new NewsAdapter(Array_news_list,Array_category, getActivity());
                        recyclerView.setAdapter(groceryAdapter);

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

}