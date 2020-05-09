package com.academy.keytone.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.academy.keytone.R;
import com.academy.keytone.networkService.AppConfig;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class AqiFragment extends Fragment {
    View view;
    TextView textView5;
    TextView tool_title,txt_date,txt_time,txt_aqi_value,txt_address;
    Toolbar toolbar;
    ImageView img_profile,back;
    TextView ll_bottom,txtdescription;
     ProgressDialog pd;
    GlobalClass globalClass;
    Shared_Preference preference;
    RelativeLayout piegraph;


    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.aqi, container, false);
        ll_bottom=view.findViewById(R.id.ll_bottom);
        piegraph=view.findViewById(R.id.piegraph);
        txt_date=view.findViewById(R.id.txt_data);
        txt_time=view.findViewById(R.id.txt_time);
        txt_aqi_value=view.findViewById(R.id.aqi_value);
        txt_address=view.findViewById(R.id.txt_address);
        txtdescription=view.findViewById(R.id.description);

        globalClass = (GlobalClass)getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd=new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        ll_bottom.bringToFront();
        toolbar = getActivity().findViewById(R.id.toolbar);
        tool_title = getActivity().findViewById(R.id.tool_title);
        // img_profile  =toolbar.findViewById(R.id.img_profile);
        img_profile  =getActivity().findViewById(R.id.img_profile);
        back  =getActivity().findViewById(R.id.back);
        img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("AQI");
        AQI();

      //  gd.setStroke(2, Color.parseColor("#f9b21a"), 5, 6);
       /* rel_tool=getActivity().findViewById(R.id.rel_tool);
        rel_tool.setVisibility(View.VISIBLE);*/
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
        tool_title.setText("AQI");
    }

    private void AQI() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

      //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_DEV+"aqi", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

               // pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {
                        JsonObject data=jobj.getAsJsonObject("aqi");

                        //  String Login = data.get("msg").getAsString().replaceAll("\"", "");
                        String title = data.get("title").getAsString().replaceAll("\"", "");
                        String title_cn = data.get("title_cn").getAsString().replaceAll("\"", "");
                        String description = data.get("description").getAsString().replaceAll("\"", "");
                        String description_cn = data.get("description_cn").getAsString().replaceAll("\"", "");
                        String location = data.get("location").getAsString().replaceAll("\"", "");
                        String location_cn = data.get("location_cn").getAsString().replaceAll("\"", "");
                        String aqi_value = data.get("aqi_value").getAsString().replaceAll("\"", "");
                        String datetime = data.get("datetime").getAsString().replaceAll("\"", "");
                        String health_concern = data.get("health_concern").getAsString().replaceAll("\"", "");
                        String color = data.get("color").getAsString().replaceAll("\"", "");
                        String newText=(Html.fromHtml(description)).toString();
                       /* String  str = newText.replaceAll("\\\\r","");
                        String  str1 = str.replaceAll("\\\\n","");*/
                        txtdescription.setText(newText);
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                        String newstr = new SimpleDateFormat("yyyy/MM/dd, HH:mm a").format(date);
                        String[] values = newstr.split(",");

                        Log.d(TAG, "onResponse: "+values[0]);
                        Log.d(TAG, "onResponse: "+newstr);
                           txt_aqi_value.setText(aqi_value);
                           txt_time.setText(values[1]);
                           txt_date.setText(values[0]);
                           txt_address.setText(location);
                           if( color.equals("Yellow")){
                               piegraph.setBackgroundResource(R.drawable.circel);  //drawable id
                               GradientDrawable gd = (GradientDrawable) piegraph.getBackground().getCurrent();
                               gd.setColor(getResources().getColor(R.color.yellow_aqi)); //set color
                           }
                           else if( color.equals("Green")){
                               piegraph.setBackgroundResource(R.drawable.circel);  //drawable id
                               GradientDrawable gd = (GradientDrawable) piegraph.getBackground().getCurrent();
                               gd.setColor(getResources().getColor(R.color.green_aqi)); //set color
                           }

                           else if( color.equals("Orange")){
                               piegraph.setBackgroundResource(R.drawable.circel);  //drawable id
                               GradientDrawable gd = (GradientDrawable) piegraph.getBackground().getCurrent();
                               gd.setColor(getResources().getColor(R.color.orange_aqi)); //set color
                           }

                           else if( color.equals("Red")){
                               piegraph.setBackgroundResource(R.drawable.circel);  //drawable id
                               GradientDrawable gd = (GradientDrawable) piegraph.getBackground().getCurrent();
                               gd.setColor(getResources().getColor(R.color.red_aqi)); //set color
                           }

                           else if( color.equals("Purple")){
                               piegraph.setBackgroundResource(R.drawable.circel);  //drawable id
                               GradientDrawable gd = (GradientDrawable) piegraph.getBackground().getCurrent();
                               gd.setColor(getResources().getColor(R.color.purple_aqi)); //set color
                           }
                           else {
                               piegraph.setBackgroundResource(R.drawable.circel);  //drawable id
                               GradientDrawable gd = (GradientDrawable) piegraph.getBackground().getCurrent();
                               gd.setColor(getResources().getColor(R.color.maron_aqi)); //set color
                           }





                      //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                     /*   Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();*/
                       // pd.dismiss();



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
                pd.dismiss();
            }
        }) {



        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}