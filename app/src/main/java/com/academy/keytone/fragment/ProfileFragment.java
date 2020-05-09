package com.academy.keytone.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.academy.keytone.R;
import com.academy.keytone.activity.HomeScreen;
import com.academy.keytone.model.PhoneList;
import com.academy.keytone.model.ResponseAPI;
import com.academy.keytone.model.SendUserDataResponse;
import com.academy.keytone.model.UserDetails;
import com.academy.keytone.networkService.AppConfig;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


import static android.app.Activity.RESULT_OK;
import static com.academy.keytone.networkService.AppConfig.user_profile_details;
import static com.academy.keytone.networkService.AppConfig.user_profile_update;
import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;
import static com.loopj.android.http.AsyncHttpClient.log;

public class ProfileFragment extends Fragment {
    String TAG="ProfileFragment";
    View view;
    TextView tool_title,txt_email,txt_school;
    TextView txt_name;
    CircularImageView img_profile;
    RelativeLayout rl_save;
    ImageView back,img,arrow;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    EditText txt_number;
    Shared_Preference preference;
    String profile_image;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    ArrayList<HashMap<String,String>> Array_email_list;
    ArrayList<HashMap<String,String>> Array_phone_list;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    File p_image;
    private List<PhoneList> phoneLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);
        img_profile=view.findViewById(R.id.img_profile_new);
        img=view.findViewById(R.id.img);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        rl_save=view.findViewById(R.id.rl_save);
        arrow=view.findViewById(R.id.arrow);
        txt_name=view.findViewById(R.id.txt_name);
        txt_school=view.findViewById(R.id.txt_school);
        txt_email=view.findViewById(R.id.txt_email);
        txt_number=view.findViewById(R.id.txt_number);
        tool_title = getActivity().findViewById(R.id.tool_title);
           back=getActivity().findViewById(R.id.back);
        rl_save.setVisibility(View.GONE);
        phoneLists = new ArrayList<>();
       if(globalClass.getRole_ids().equals("2")){
         txt_school.setText("Teacher");
       }
       else if(globalClass.getRole_ids().equals("3")){
           txt_school.setText("Parent");
       }
       else if(globalClass.getRole_ids().equals("4")){
           txt_school.setText("Guest");

       }
      //  circularImageView=getActivity().findViewById(R.id.img_profile);
     //   img_profile.setVisibility(View.GONE);
        ButterKnife.bind(getActivity());
        back.setVisibility(View.VISIBLE);
        tool_title.setText("MY PROFILE");

        Array_email_list = new ArrayList<>();
        Array_phone_list = new ArrayList<>();
        Profile();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                txt_number.setBackgroundResource(0);
                txt_number.setSelection(txt_number.getText().length());
                txt_number.setEnabled(true);
                txt_number.setClickable(true);
                txt_number.setFocusable(true);
                txt_number.setFocusableInTouchMode(true);
                txt_number.requestFocus();
                txt_number.setCursorVisible(true);
                rl_save.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);


            }
        });

        txt_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){

                    if(isValidMobile(txt_number.getText().toString())){
                        txt_number.setClickable(false);
                        txt_number.setFocusableInTouchMode(false);
                        txt_number.setEnabled(false);
                        txt_number.setFocusable(false);
                        arrow.setVisibility(View.VISIBLE);
                        String text_numeber=txt_number.getText().toString().trim();
                        UpdateNumber(text_numeber);
                    }
                    else {
                        Toast.makeText(getActivity(), "Please enter 11 digits mobile number", Toast.LENGTH_LONG).show();
                    }
                    //do something

                }
                return false;
            }
        });






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), HomeScreen.class);
                startActivity(intent);
            }

        });




        return view;
    }
    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >= 11 && phone.length() <= 11;
        }
        return false;
    }
    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permisssion","not granted");


                    if (shouldShowRequestPermissionRationale(permissions[i])) {

                        Log.d("if","if");
                        permissionsNeeded.add(perm);

                    } else {
                        // add the request.
                        Log.d("else","else");
                        permissionsNeeded.add(perm);
                    }

                }
            }
        }

        if (permissionsNeeded.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // go ahead and request permissions
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), permRequestCode);
            }
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

       // img_profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tool_title.setText("MY PROFILE");
    }


    private void Profile() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

      //  progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+user_profile_details, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "user_profile_details Response: " + response.toString());

               // progressDialog.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");
                    String  user_image_url = jobj.get("user_image_url").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {
                        JsonObject data=jobj.getAsJsonObject("user_details");
                        //  String Login = data.get("msg").getAsString().replaceAll("\"", "");
                        String email = data.get("user_email").getAsString().replaceAll("\"", "");
                        String user_id = data.get("user_id").getAsString().replaceAll("\"", "");
                        String role_ids = data.get("role_ids").getAsString().replaceAll("\"", "");
                        String group_ids = data.get("group_ids").getAsString().replaceAll("\"", "");
                        String department_ids = data.get("department_ids").getAsString().replaceAll("\"", "");
                        String is_first_login = data.get("is_first_login").getAsString().replaceAll("\"", "");
                        String fname = data.get("fname").getAsString().replaceAll("\"", "");
                        String lname = data.get("lname").getAsString().replaceAll("\"", "");
                        profile_image= data.get("profile_image").getAsString().replaceAll("\"", "");
                        RequestOptions myOptions = new RequestOptions()
                                .override(100, 100);
                        String image_upload=user_image_url+profile_image;
                        Log.d(TAG, "onResponse: "+image_upload);
                        Log.d(TAG, "onResponseProfile: "+profile_image);


                        txt_name.setText(fname+" "+lname);
                        txt_email.setText(email);

                        Picasso.get().load(image_upload).into(img_profile);
                        JsonArray email_list=data.getAsJsonArray("email_list");
                        // JSONArray email_list = data.getJSONArray("email_list");
                        for (int j = 0; j < email_list.size(); j++) {
                            JsonObject images1 = email_list.get(j).getAsJsonObject();

                            String product_sub_id = images1.get("email").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("email",product_sub_id);
                            Array_email_list.add(hashMap);
                            Log.d(TAG, "Hashmap1 " + hashMap);

                        }
                        JsonArray phone_list=data.getAsJsonArray("phone_list");
                        for (int j = 0; j < phone_list.size(); j++) {
                            JsonObject images1 = phone_list.get(j).getAsJsonObject();
                            String phone = images1.get("phone").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("phone",phone);
                            Array_phone_list.add(hashMap);
                          //  txt_number.setText(Array_phone_list);

                            Log.d(TAG, "Hashmap1 " +  Array_phone_list.get(0).get("phone"));
                            txt_number.setText(Array_phone_list.get(0).get("phone"));


                        }

                       /* globalClass.setEmail(email);
                        globalClass.setId(user_id);
                        globalClass.setFname(fname);
                        globalClass.setLname(lname);
                        globalClass.setIslogin(is_first_login);
                        globalClass.setRole_ids(role_ids);
                        globalClass.setGroup_ids(group_ids);
                        globalClass.setP_image(profile_image);
                        globalClass.setDepartment_ids(department_ids);
                        globalClass.setProfil_pic(user_image_url+profile_image);
                        String url=globalClass.getProfil_pic();
                        preference.savePrefrence();*/

                      //    Picasso.get().load(image_upload).error(R.mipmap.index).into(img_profile);




                    }
                    else {

                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(getActivity(),"Some thing went wrong", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", globalClass.getId());



                Log.d(TAG, "user_profile_details: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void UpdateNumber(final String number) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        Array_phone_list.clear();
        Array_email_list.clear();
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+user_profile_update, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "user_profile_details Response: " + response.toString());

                progressDialog.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");
                    String  user_image_url = jobj.get("user_image_url").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {
                        JsonObject data=jobj.getAsJsonObject("user_details");
                        //  String Login = data.get("msg").getAsString().replaceAll("\"", "");
                        String email = data.get("user_email").getAsString().replaceAll("\"", "");
                        String user_id = data.get("user_id").getAsString().replaceAll("\"", "");
                        String role_ids = data.get("role_ids").getAsString().replaceAll("\"", "");
                        String group_ids = data.get("group_ids").getAsString().replaceAll("\"", "");
                        String department_ids = data.get("department_ids").getAsString().replaceAll("\"", "");
                        String is_first_login = data.get("is_first_login").getAsString().replaceAll("\"", "");
                        String fname = data.get("fname").getAsString().replaceAll("\"", "");
                        String lname = data.get("lname").getAsString().replaceAll("\"", "");
                        String profile_image = data.get("profile_image").getAsString().replaceAll("\"", "");
                        JsonArray email_list=data.getAsJsonArray("email_list");
                        // JSONArray email_list = data.getJSONArray("email_list");
                        for (int j = 0; j < email_list.size(); j++) {
                            JsonObject images1 = email_list.get(j).getAsJsonObject();

                            String product_sub_id = images1.get("email").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("email",product_sub_id);
                            Array_email_list.add(hashMap);
                            Log.d(TAG, "Hashmap1 " + hashMap);

                        }
                        JsonArray phone_list=data.getAsJsonArray("phone_list");
                        for (int j = 0; j < phone_list.size(); j++) {
                            JsonObject images1 = phone_list.get(j).getAsJsonObject();
                            String phone = images1.get("phone").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("phone",phone);
                            Array_phone_list.add(hashMap);
                          //  txt_number.setText(Array_phone_list);

                            Log.d(TAG, "Hashmap1 " +  Array_phone_list.get(0).get("phone"));
                            txt_number.setText(Array_phone_list.get(0).get("phone"));


                        }

                       /* globalClass.setEmail(email);
                        globalClass.setId(user_id);
                        globalClass.setFname(fname);
                        globalClass.setLname(lname);
                        globalClass.setIslogin(is_first_login);
                        globalClass.setRole_ids(role_ids);
                        globalClass.setGroup_ids(group_ids);
                        globalClass.setDepartment_ids(department_ids);
                        globalClass.setProfil_pic(user_image_url+profile_image);
                        String url=globalClass.getProfil_pic();
                        preference.savePrefrence();*/
                        String image_upload=user_image_url+profile_image;
                        Log.d(TAG, "onResponse: "+globalClass.getProfil_pic());
                        txt_name.setText(fname+" "+lname);
                        txt_email.setText(email);
                        // Profile();
                        Picasso.get().load(image_upload).error(R.mipmap.index).into(img_profile);




                    }
                    else {

                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(getActivity(),"Some thing went wrong", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("user_id", globalClass.getId());
                params.put("fname", globalClass.getFname());
                params.put("lname", globalClass.getLname());
                params.put("profile_image", profile_image);
                params.put("phone",number);



                Log.d(TAG, "user_profile_details: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    public void updateProfile(){

        progressDialog.show();

        String url = AppConfig.URL_DEV+user_profile_update;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        cl.setSSLSocketFactory(
                new SSLSocketFactory(getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));
              params.put("user_id",globalClass.getId());
              params.put("fname", globalClass.getFname());
              params.put("lname", globalClass.getLname());
              params.put("profile_image",profile_image);
              params.put("phone",txt_number.getText().toString());
        try{

            params.put("profile_avatar",p_image);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(3 , DEFAULT_TIMEOUT);


        cl.post(url,params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess: "+response.toString());
                if (response != null) {
                    Gson gson = new Gson();


                    JsonObject jobj = gson.fromJson(String.valueOf(response), JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");
                    String  user_image_url = jobj.get("user_image_url").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {
                        JsonObject data=jobj.getAsJsonObject("user_details");
                        //  String Login = data.get("msg").getAsString().replaceAll("\"", "");
                        String email = data.get("user_email").getAsString().replaceAll("\"", "");
                        String user_id = data.get("user_id").getAsString().replaceAll("\"", "");
                        String role_ids = data.get("role_ids").getAsString().replaceAll("\"", "");
                        String group_ids = data.get("group_ids").getAsString().replaceAll("\"", "");
                        String department_ids = data.get("department_ids").getAsString().replaceAll("\"", "");
                        String is_first_login = data.get("is_first_login").getAsString().replaceAll("\"", "");
                        String fname = data.get("fname").getAsString().replaceAll("\"", "");
                        String lname = data.get("lname").getAsString().replaceAll("\"", "");
                        String profile_image = data.get("profile_image").getAsString().replaceAll("\"", "");
                        JsonArray email_list=data.getAsJsonArray("email_list");
                        // JSONArray email_list = data.getJSONArray("email_list");
                        for (int j = 0; j < email_list.size(); j++) {
                            JsonObject images1 = email_list.get(j).getAsJsonObject();

                            String product_sub_id = images1.get("email").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("email",product_sub_id);
                            Array_email_list.add(hashMap);
                            Log.d(TAG, "Hashmap1 " + hashMap);

                        }
                        JsonArray phone_list=data.getAsJsonArray("phone_list");
                        for (int j = 0; j < phone_list.size(); j++) {
                            JsonObject images1 = phone_list.get(j).getAsJsonObject();
                            String phone = images1.get("phone").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("phone",phone);
                            Array_phone_list.add(hashMap);
                          //  txt_number.setText(Array_phone_list);

                            Log.d(TAG, "Hashmap1 " +  Array_phone_list.get(0).get("phone"));
                            txt_number.setText(Array_phone_list.get(0).get("phone"));

                        }

                        /*globalClass.setEmail(email);
                        globalClass.setId(user_id);
                        globalClass.setFname(fname);
                        globalClass.setLname(lname);
                        globalClass.setIslogin(is_first_login);
                        globalClass.setRole_ids(role_ids);
                        globalClass.setGroup_ids(group_ids);
                        globalClass.setP_image(profile_image);
                        globalClass.setDepartment_ids(department_ids);
                        globalClass.setProfil_pic(user_image_url+profile_image);
                        String url=globalClass.getProfil_pic();
                        preference.savePrefrence();*/
                        String image_upload=user_image_url+profile_image;
                        Log.d(TAG, "onResponse: "+globalClass.getProfil_pic());
                        txt_name.setText(fname+" "+lname);
                        txt_email.setText(email);
                         // Profile();
                          Picasso.get().load(image_upload).error(R.mipmap.index).into(img_profile);



                        }else{

                            Toast.makeText(getActivity(), message,Toast.LENGTH_LONG).show();


                        }

                    progressDialog.dismiss();

                }


                // pd.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
               super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }

        });


    }

    public SSLContext getSslContext() {

        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };

        SSLContext sslContext=null;

        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }



    private void selectImage() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            p_image = new File(getRealPathFromURI(uri));

            int file_size = Integer.parseInt(String.valueOf(p_image.length()/1024));
            Log.d(TAG, "image = "+p_image);
            Log.d(TAG, "file_size = "+file_size);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                bitmap = getResizedBitmap(bitmap, 480, 520);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                img_profile.setImageBitmap(bitmap);
              //  updateProfile();
           sendIntruderDataToServer(globalClass.getId(),globalClass.getFname(),globalClass.getLname(),p_image,profile_image,
                      txt_number.getText().toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {


            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }


            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);

/*
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);*/

                Log.d(TAG, "bitmap: "+bitmap);

                img_profile.setImageBitmap(bitmap);

                String path = Environment.getExternalStorageDirectory()+File.separator;
                // + File.separator
                //   + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {

                    p_image = file;

                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
                    sendIntruderDataToServer(globalClass.getId(),globalClass.getFname(),globalClass.getLname(),p_image,profile_image,
                            txt_number.getText().toString());
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // iv_product_image.setImageBitmap(photo);
        }
        if(globalClass.isNetworkAvailable()){
            // user_profile_pic_update_url();
        }else{
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();        }

    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
        public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private void sendIntruderDataToServer(String user_id, String fname,String lname,File file,String profile_image,String phone) {
       // progressDialog.show();
        Log.e("user_id",user_id);
        Log.e("fname",fname);
        Log.e("lname",lname);
        Log.e("file", String.valueOf(file));
        Log.e("profile_image",profile_image);
        Log.e("phone",phone);
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        IApis uploadAPIs = retrofit.create(IApis.class);
        // Create a request body with file and image media type

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("profile_avatar", file.getName(), fileReqBody);
        Log.d(TAG, "sendIntruderDataToServer: "+part);
        //Create request body with text description and text media type
        RequestBody user_idBody = RequestBody.create(MediaType.parse("text/plain"), user_id);
       RequestBody locationBody = RequestBody.create(MediaType.parse("text/plain"), profile_image);
        RequestBody fnameBody = RequestBody.create(MediaType.parse("text/plain"), fname);
        RequestBody lnameBody = RequestBody.create(MediaType.parse("text/plain"), lname);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);
        Log.d(TAG, "sendIntruderDataToServer: "+user_idBody);
        Log.d(TAG, "sendIntruderDataToServer: "+locationBody);
        Log.d(TAG, "sendIntruderDataToServer: "+fnameBody);
        Log.d(TAG, "sendIntruderDataToServer: "+lnameBody);
        Log.d(TAG, "sendIntruderDataToServer: "+phoneBody);

        //
        Call<ResponseAPI> call = uploadAPIs.uploadImage(part,user_idBody,fnameBody,lnameBody,locationBody,phoneBody);
        call.enqueue(new Callback<ResponseAPI>() {


            @Override
            public void onResponse(@NonNull Call<ResponseAPI> call, @NonNull retrofit2.Response<ResponseAPI> response) {
                String getResponse = new Gson().toJson(response.body());
                Log.d(TAG, "response:" + response.body());

                Log.d(LOG_TAG, "qrcodecheckresponse:" + getResponse);

             // progressDialog.dismiss();
                if (response.body() != null) {
                    ResponseAPI sendUserDataResponse=response.body();
                    Log.d(TAG, "sendUserDataResponse: "+sendUserDataResponse.getStatus());
                    UserDetails userDetails=response.body().getUserDetails();
                    Log.d(TAG, "onResponse: "+userDetails.getFname());
                    String image_upload=response.body().getUserImageUrl()+userDetails.getProfileImage();
                    phoneLists = userDetails.getPhoneList();
                    Log.d(TAG, "onResponse: "+phoneLists.get(0).getPhone());
                    Picasso.get().load(image_upload).error(R.mipmap.index).into(img_profile);
                    txt_name.setText(fname+" "+lname);
                    Log.d(TAG, "onResponse: "+userDetails.getUserEmail());
                    Log.d(TAG, "onResponse: "+userDetails.getProfileImage());
                    Log.d(TAG, "onResponse: "+image_upload);
                    txt_email.setText(userDetails.getUserEmail());
                    txt_number.setText(phoneLists.get(0).getPhone());


                }
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAPI> call, @NonNull Throwable t) {

                Log.d(LOG_TAG, "onFailure: " + t.getLocalizedMessage());
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}