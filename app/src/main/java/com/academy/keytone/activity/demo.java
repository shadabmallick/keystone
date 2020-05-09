package com.academy.keytone.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.academy.keytone.R;
import com.academy.keytone.adapter.BannerAdapter;
import com.academy.keytone.adapter.ViewPagerAdapter;
import com.academy.keytone.model.SliderUtils;
import com.academy.keytone.util.CustomVolleyRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class demo extends AppCompatActivity {
    String request_url = "https://testsite.co.in/Keystone/Api/home";
    private BannerAdapter bannerAdapter;
    private Handler handler = new Handler();
    private Timer swipeTimer;
    private Runnable Update;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    ViewPager viewPager;
    ArrayList<HashMap<String,String>> bannerList;
    LinearLayout layout_dot;
    TextView[] dot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        bannerList = new ArrayList<>();
        layout_dot = (LinearLayout) findViewById(R.id.layout_dot);
        viewPager = findViewById(R.id.viewPager_banner);
        //addDot(0);
        browseJob();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                addDot(i);
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                request_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                // pd.dismiss();


                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    String banner_url = jobj.get("banner_url").toString().replaceAll("\"", "");

                    SliderUtils sliderUtils = new SliderUtils();
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
                            sliderUtils.setSliderImageUrl(banner_url+images1.get("image").toString().replaceAll("\"", ""));
                            sliderUtils.setName(images1.get("name").toString().replaceAll("\"", ""));
                            Log.d(TAG, "onResponse: "+banner_url+images1.get("image").toString().replaceAll("\"", ""));
                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("image",banner_url+image);
                            hashMap.put("name",name);
                            bannerList.add(hashMap);

                            initViewPager(bannerList);


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

            }
        }) {



        };

        // Adding request to request queue
        CustomVolleyRequest.getInstance(getApplicationContext()).addToRequestQueue(strReq);


    }
    private void initViewPager(ArrayList<HashMap<String,String>> arrayList){

        bannerAdapter = new BannerAdapter(getApplicationContext(), arrayList);
        viewPager.setAdapter(bannerAdapter);


        NUM_PAGES = arrayList.size();

        Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        handler.removeCallbacks(Update);


        if (swipeTimer == null){
            swipeTimer = new Timer();

            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 4000, 4000);

            handler.postDelayed(Update,4000);
        }

    }
    public void addDot(int page_position) {
        dot = new TextView[bannerList.size()];
        layout_dot.removeAllViews();

        for (int i = 0; i < dot.length; i++) {;
            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(10);
            dot[i].setTextColor(getResources().getColor(R.color.white));
            layout_dot.addView(dot[i]);
        }
        //active dot
        dot[page_position].setTextColor(getResources().getColor(R.color.orange_aqi));
    }
}
