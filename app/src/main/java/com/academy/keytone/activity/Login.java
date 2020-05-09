package com.academy.keytone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.academy.keytone.networkService.AppConfig.login;

public class Login extends AppCompatActivity {
    String TAG="Login";
    RelativeLayout rl_login;
    EditText edtUsername,edtPassword;
    //UserService userService;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    ImageView iv_eye;
    boolean password_visible = false;
    TextView tv_register,password;
    ArrayList<HashMap<String,String>> Array_email_list;
    ArrayList<HashMap<String,String>> Array_phone_list;

    String device_id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        device_id  = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        edtUsername =  findViewById(R.id.edt_user_id);
        edtPassword =  findViewById(R.id.edt_password);
        rl_login =  findViewById(R.id.rl_login);
        tv_register =  findViewById(R.id.tv_register);
        iv_eye =  findViewById(R.id.iv_eye);
        password =  findViewById(R.id.password);
        Array_email_list = new ArrayList<>();
        Array_phone_list = new ArrayList<>();
        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"You clicked on EditText drawable image ", Toast.LENGTH_LONG).show();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(intent);
            }
        });

        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible) {

                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.iconseye);

                    password_visible = true;

                } else {

                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.iconeye);

                    password_visible = false;

                }

                edtPassword.setSelection(edtPassword.length());
            }
        });

        //  userService = ApiUtils.getUserService();

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(validateLogin(username, password)){
                    login(username, password);
                }
            }
        });



    }

    private void login(final String user_email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+login, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

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
                        String email = data.get("user_email").getAsString().replaceAll("\"", "");
                        String user_id = data.get("user_id").getAsString().replaceAll("\"", "");
                        String role_ids = data.get("role_ids").getAsString().replaceAll("\"", "");
                        String group_ids = data.get("group_ids").getAsString().replaceAll("\"", "");
                        String department_ids = data.get("department_ids").getAsString().replaceAll("\"", "");
                        String is_first_login = data.get("is_first_login").getAsString().replaceAll("\"", "");
                        String fname = data.get("fname").getAsString().replaceAll("\"", "");
                        String lname = data.get("lname").getAsString().replaceAll("\"", "");
                        String profile_image = data.get("profile_image").getAsString().replaceAll("\"", "");
                        if(is_first_login.equals("0")){
                            Intent intent=new Intent(getApplicationContext(), ChangePassword.class);
                            startActivity(intent);
                        }
                        JsonArray email_list=data.getAsJsonArray("email_list");
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
                            Log.d(TAG, "Hashmap1 " + hashMap);

                        }

                        globalClass.setEmail(email);
                        globalClass.setId(user_id);
                        globalClass.setFname(fname);
                        globalClass.setLname(lname);
                        globalClass.setIslogin(is_first_login);
                        globalClass.setRole_ids(role_ids);
                        globalClass.setGroup_ids(group_ids);
                        globalClass.setDepartment_ids(department_ids);
                        globalClass.setP_image(profile_image);
                        globalClass.setProfil_pic(user_image_url+profile_image);

                        globalClass.setLogin_status(true);


                        preference.savePrefrence();

                        Intent intent = new Intent(Login.this, HomeScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);                            startActivity(intent);




                    }
                    else {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("password", password);
                params.put("email", user_email);
                params.put("device_id", device_id);
                params.put("device_type", "android");


                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


/*
    private void login(final String user_email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        final String device_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("TAG", "login: "+device_id);

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.login, response -> {
                    Log.d(TAG, "Login Response: " + response.toString());

                    if (response != null){
                        progressDialog.dismiss();
                        try {

                            JSONObject main_object = new JSONObject(response);


                            int status = main_object.optInt("status");
                            String  user_image_url = main_object.optString("user_image_url");
                            String message = main_object.optString("message");

                            if (status == 1){

                                Toast.makeText(getApplicationContext(), message,
                                        Toast.LENGTH_LONG).show();


                                JSONObject data = main_object.getJSONObject("user_details");


                                String email = data.optString("user_email");
                                String user_id = data.optString("user_id");
                                String role_ids = data.optString("role_ids");
                                String group_ids = data.optString("group_ids");
                                String department_ids = data.optString("department_ids");
                                String is_first_login = data.optString("is_first_login");
                                String fname = data.optString("fname");
                                String lname = data.optString("lname");
                                String profile_image = data.optString("profile_image");

                                JSONArray email_list = data.getJSONArray("email_list");
                                for (int j = 0; j < email_list.length(); j++) {
                                   JSONObject images1=email_list.getJSONObject(j);
                                    String product_sub_id = images1.get("email").toString().replaceAll("\"", "");

                                    HashMap<String, String> hashMap = new HashMap<>();

                                    hashMap.put("email",product_sub_id);
                                    Array_email_list.add(hashMap);
                                    Log.d(TAG, "Hashmap1 " + hashMap);

                                }
                                JSONArray phone_list = data.getJSONArray("phone_list");
                                for (int j = 0; j < phone_list.length(); j++) {
                                   JSONObject images1=phone_list.getJSONObject(j);
                                    String phone = images1.get("phone").toString().replaceAll("\"", "");

                                    HashMap<String, String> hashMap = new HashMap<>();

                                    hashMap.put("phone",phone);
                                    Array_email_list.add(hashMap);
                                    Log.d(TAG, "Hashmap1 " + hashMap);

                                }

                                globalClass.setEmail(email);
                                globalClass.setId(user_id);
                                globalClass.setFname(fname);
                                globalClass.setLname(lname);
                                globalClass.setIslogin(is_first_login);
                                globalClass.setProfil_pic(user_image_url+profile_image);

                                globalClass.setLogin_status(true);


                                preference.savePrefrence();

                                Intent intent = new Intent(Login.this, HomeScreen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);                            startActivity(intent);



                            }else {

                                Toast.makeText(getApplicationContext(), message,
                                        Toast.LENGTH_LONG)
                                        .show();


                            }


                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), "Connection error",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }

                    }

                }, error -> {
            Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            progressDialog.dismiss();
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("password", password);
                params.put("email", user_email);
                params.put("device_id", device_id);
                params.put("device_type", "android");


                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
*/

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





}