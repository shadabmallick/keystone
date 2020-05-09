package com.academy.keytone.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.academy.keytone.adapter.BannerAdapter;
import com.academy.keytone.util.GlobalClass;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.academy.keytone.R;
import com.academy.keytone.activity.CustomGridViewActivity;
import com.academy.keytone.adapter.EventAdapter;
import com.academy.keytone.adapter.NoticeAdapter;
import com.academy.keytone.adapter.ViewPagerAdapter;
import com.academy.keytone.model.Event;
import com.academy.keytone.model.SliderUtils;
import com.academy.keytone.model.notice;
import com.academy.keytone.util.CustomVolleyRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;
import static java.lang.System.exit;

public class HomeFragment extends Fragment implements CustomGridViewActivity.onItemClickListner {
    View view;
    TextView tool_title;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    RelativeLayout rel_tool;
    private static int NUM_PAGES = 3;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    //  public static ArrayList<HashMap<String,String>> list_names;
    int currentPage = 0;
    private BannerAdapter bannerAdapter;
    private Handler handler = new Handler();
    private Timer swipeTimer;
    private Runnable Update;

    ArrayList<HashMap<String,String>> bannerList;
    LinearLayout layout_dot;
    TextView[] dot;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;
    GlobalClass globalClass;
    String request_url = "https://testsite.co.in/Keystone/Api/home";
    private List<notice> groceryList = new ArrayList<>();
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView;
    private NoticeAdapter groceryAdapter;
    private EventAdapter eventAdapter;
    Toolbar toolbar;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    GridView androidGridView;
    TabLayout tabs;
    String[] gridViewString = {
            "AQI", "IN THE LOOP", "HOS CORNER", "WEEKLY BULLETIN", "ON THE AIR", "PUBLICATIONS",
            "WEEKLY MENU", "USEFUL LINKS",

    } ;
    String[] gridViewString1 = {
            "AQI", "IN THE LOOP", "HOS CORNER", "CONNECT US", "ON THE AIR", "PUBLICATIONS",
            "WEEKLY MENU", "USEFUL LINKS",

    } ;
    int[] gridViewImageId = {
            R.mipmap.iconaqi,R.mipmap.loop, R.mipmap.hos,R.mipmap.weekly_bulletin, R.mipmap.onair, R.mipmap.publication,R.mipmap.weekly,R.mipmap.link


    };
    int[] gridViewImageId1 = {
            R.mipmap.iconaqi,R.mipmap.loop, R.mipmap.hos,R.mipmap.contact, R.mipmap.onair, R.mipmap.publication,R.mipmap.weekly,R.mipmap.link


    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homepage, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        globalClass=(GlobalClass) getActivity().getApplicationContext();
        tool_title = getActivity().findViewById(R.id.tool_title);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        ll_setting  =getActivity().findViewById(R.id.ll_setting);
        back  =getActivity().findViewById(R.id.back);
        rel_tool = getActivity().findViewById(R.id.rel_tool);
        bannerList = new ArrayList<>();
        layout_dot = view.findViewById(R.id.SliderDots);
        viewPager = view.findViewById(R.id.viewPager);
      //  rel_tool=getActivity().findViewById(R.id.rel_tool);
     //   rel_tool.setVisibility(View.GONE);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        toolbar.setVisibility(View.VISIBLE);
        rel_tool.setVisibility(View.VISIBLE);
        tool_title.setText("KEYSTONE");
        groceryRecyclerView = view.findViewById(R.id.recycler1);
        recyclerView = view.findViewById(R.id.recycler2);
        rq = CustomVolleyRequest.getInstance(getActivity()).getRequestQueue();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
               // addDot(i);

            }
            @Override
            public void onPageSelected(int i) {
              //  addDot(i);
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });




        browseJob();



        groceryAdapter = new NoticeAdapter(groceryList, getActivity());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        groceryRecyclerView.setLayoutManager(horizontalLayoutManager);
        groceryRecyclerView.setAdapter(groceryAdapter);

        eventAdapter = new EventAdapter(groceryList1, getActivity());
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager1);
        recyclerView.setAdapter(eventAdapter);

        if(globalClass.getRole_ids().equals("2")){
            CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(getActivity(), gridViewString, gridViewImageId,this);
            androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
            androidGridView.setAdapter(adapterViewAndroid);
        }
        else {
            CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(getActivity(), gridViewString1, gridViewImageId1,this);
            androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
            androidGridView.setAdapter(adapterViewAndroid);
        }

        populategroceryList();
        populategroceryList1();
        return view;
    }
    private void populategroceryList(){
        groceryList.clear();
        notice notice1 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice2 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice3 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice4 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice5 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");
        notice notice6 = new notice("Morning Assembly Cancelled ","Junior High school assembly has been cancelled today due to flu",
                "40 min ago");

        groceryList.add(notice1);
        groceryList.add(notice2);
        groceryList.add(notice3);
        groceryList.add(notice4);
        groceryList.add(notice5);
        groceryList.add(notice6);
       // groceryAdapter.notifyDataSetChanged();
    }
    private void populategroceryList1(){
        groceryList1.clear();
        Event event1 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.event1,"Duration: 6hrs","Fees: Free");

        Event event2 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.event,"Duration: 6hrs","Fees: Free");
        Event event3 = new Event("April 23rd Saturday ","Weekend Hutong tour",
                "Start: 10am@South Gate",R.mipmap.event2,"Duration: 6hrs","Fees: Free");



        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
        groceryList1.add(event1);
        groceryList1.add(event2);
        groceryList1.add(event3);
       // eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int i) {
        Log.d(TAG, "onItemClick: "+i);
        switch(i) {
            case 0:
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment myFragment = new AqiFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                break;
            case 1:
                AppCompatActivity activity1 = (AppCompatActivity) getContext();
                Fragment myFragment1 = new InTheLoopFragment();
                activity1.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment1).addToBackStack(null).commit();
                break;
            case 2:
                AppCompatActivity activity2 = (AppCompatActivity) getContext();
                Fragment myFragment2= new HosFragment();
                activity2.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment2).addToBackStack(null).commit();
                break;

            case 3:
                AppCompatActivity activity3 = (AppCompatActivity) getContext();
                Fragment myFragment3= new ContactFragment();
                activity3.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment3).addToBackStack(null).commit();
                break;
            case 4:
                AppCompatActivity activity4 = (AppCompatActivity) getContext();
                Fragment myFragment4= new OnTheAirFragment();
                activity4.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment4).addToBackStack(null).commit();
                break;

            case 5:
                AppCompatActivity activity5 = (AppCompatActivity) getContext();
                Fragment myFragment5= new PublicationFragment();
                activity5.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment5).addToBackStack(null).commit();
                break;

            case 7:
                AppCompatActivity activity6 = (AppCompatActivity) getContext();
                Fragment myFragment6= new UsefulLink();
                activity6.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment6).addToBackStack(null).commit();
                break;

            default:
        }


    }
    @Override
    public boolean onBackPressed() {
        exit(0);
       return false;
    }
    @Override
    public void onResume() {
        super.onResume();
        tool_title.setText("KEYSTONE");
        img_profile.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        ll_setting.setVisibility(View.GONE);
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
                            hashMap.put("link",link);
                            bannerList.add(hashMap);

                            initViewPager(bannerList);


                        }

                    //    dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.circle_orange));


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
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "DATA NOT FOUND", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getActivity(), "NO NETWORK CONNECTION", Toast.LENGTH_LONG).show();

            }
        }) {



        };

        // Adding request to request queue
        CustomVolleyRequest.getInstance(getActivity()).addToRequestQueue(strReq);


    }
    private void initViewPager(ArrayList<HashMap<String,String>> arrayList){

        bannerAdapter = new BannerAdapter(getActivity(), arrayList);
        viewPager.setAdapter(bannerAdapter);
        addDot(0);


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
            dot[i] = new TextView(getContext());
            dot[i].setText(Html.fromHtml("&#9679;"));
            dot[i].setTextSize(10);
            dot[i].setTextColor(getResources().getColor(R.color.white));
            layout_dot.addView(dot[i]);
        }
        //active dot
        dot[page_position].setTextColor(getResources().getColor(R.color.orange_aqi));
    }
}