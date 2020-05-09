package com.academy.keytone.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.academy.keytone.R;
import com.academy.keytone.fragment.CalenderFragment;
import com.academy.keytone.fragment.EventFragment;
import com.academy.keytone.fragment.HomeFragment;
import com.academy.keytone.fragment.MessageFragment;
import com.academy.keytone.fragment.NewsFragment;
import com.academy.keytone.fragment.ProfileFragment;
import com.academy.keytone.fragment.SettingFragment;
import com.academy.keytone.networkService.AppConfig;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class HomeScreen extends AppCompatActivity{
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    TextView textView5,tool_title;
    View view1,view2,view3,view4,view5;
    Toolbar toolbar;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    RelativeLayout rel_tool;
    GlobalClass globalClass;
    ProgressDialog pd;
   public static ArrayList<HashMap<String,String>> list_names;
    Shared_Preference prefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img_profile  =toolbar.findViewById(R.id.img_profile);
        back  =toolbar.findViewById(R.id.back);
        tool_title = findViewById(R.id.tool_title);
        tool_title.setText("KEYSTONE");
        globalClass = (GlobalClass)getApplicationContext();
        prefrence = new Shared_Preference(getApplicationContext());
        prefrence.loadPrefrence();
        pd = new ProgressDialog(getApplicationContext());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
       // list_names = new ArrayList<>();

       // browseJob();
        view1=findViewById(R.id.viewid_1);
        ll_setting=findViewById(R.id.ll_setting);
        rel_tool=findViewById(R.id.rel_tool);
        ll_profile=findViewById(R.id.ll_profile);
        view2=findViewById(R.id.viewid_2);
        view3=findViewById(R.id.viewid_3);
        view4=findViewById(R.id.viewid_4);
        view5=findViewById(R.id.viewid_5);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileFragment frag = new ProfileFragment();
                tool_title.setText(getResources().getString(R.string.profile));
                img_profile.setVisibility(View.GONE);
               back.setVisibility(View.VISIBLE);
                ll_setting.setVisibility(View.VISIBLE);
                switchToFragment6();
            }
        });
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingFragment frag = new SettingFragment();
                tool_title.setText(getResources().getString(R.string.settings));
                img_profile.setVisibility(View.GONE);
               back.setVisibility(View.VISIBLE);
                ll_setting.setVisibility(View.GONE);
                switchToFragment7();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            img_profile.setVisibility(View.VISIBLE);
                       //     back.setVisibility(View.GONE);
                            tool_title.setText(getResources().getString(R.string.key));

                            switchToFragment1();

                            view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            view5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            break;
                       case R.id.navigation_sms:
                           tool_title.setText(getResources().getString(R.string.news));
                            img_profile.setVisibility(View.GONE);
                          back.setVisibility(View.GONE);
                           switchToFragment2();
                           view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            break;
                       case R.id.event:
                           tool_title.setText(getResources().getString(R.string.event));
                           img_profile.setVisibility(View.GONE);
                         back.setVisibility(View.GONE);
                           switchToFragment3();
                           view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            break;

                            case R.id.calender:
                                switchToFragment5();
                           view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                           view5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                            break;

                            case R.id.message:
                                tool_title.setText(getResources().getString(R.string.message));
                                img_profile.setVisibility(View.GONE);
                                switchToFragment4();
                           view1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                           view5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            break;

                    }

                  /*  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                    return true;
                }
            };
    public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
    }

    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new EventFragment()).commit();
    }


    public void switchToFragment4() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
    }

    public void switchToFragment5() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new CalenderFragment()).commit();
    }


    public void switchToFragment6() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }


    public void switchToFragment7() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
    }

   /* public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }*/
   @Override
   public void onBackPressed(){
       android.app.FragmentManager fm = getFragmentManager();
       if (fm.getBackStackEntryCount() > 0) {
           Log.i("MainActivity", "popping backstack");
           fm.popBackStack();
       } else {
           Log.i("MainActivity", "nothing on backstack, calling super");
           super.onBackPressed();
       }
   }

    private void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

      //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

               // pd.dismiss();
                list_names.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    String banner_url = jobj.get("banner_url").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("banners");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String image = images1.get("image").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String name_cn = images1.get("name_cn").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String description_cn = images1.get("description_cn").toString().replaceAll("\"", "");
                            String link = images1.get("link").toString().replaceAll("\"", "");
                            String enable_date = images1.get("enable_date").toString().replaceAll("\"", "");
                            String disable_date = images1.get("disable_date").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("image", banner_url+image);
                            hashMap.put("name", name);
                            hashMap.put("name_cn", name_cn);
                            hashMap.put("description", description);
                            hashMap.put("description_cn", description_cn);
                            hashMap.put("link", link);
                            hashMap.put("enable_date", enable_date);
                            hashMap.put("disable_date", disable_date);


                            list_names.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        JsonArray upcoming_events=jobj.getAsJsonArray("upcoming_events");

                        for (int i = 0; i < upcoming_events.size(); i++) {
                            JsonObject images1 = upcoming_events.get(i).getAsJsonObject();
                            String installation_price = images1.get("installation_price").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("installation_price", installation_price);


                            //  productDetaiils.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }
                        JsonArray notice_today=jobj.getAsJsonArray("notice_today");

                        for (int i = 0; i < notice_today.size(); i++) {
                            JsonObject images1 = notice_today.get(i).getAsJsonObject();
                            String installation_price = images1.get("installation_price").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("installation_price", installation_price);

                            //  productDetaiils.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }


                    } else
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "DATA NOT FOUND", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "DATA NOT FOUND", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", globalClass.getId());
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    }

