package com.academy.keytone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.academy.keytone.R;
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
import java.util.Map;

import static com.academy.keytone.networkService.AppConfig.change_password;
import static com.academy.keytone.networkService.AppConfig.login;

public class ChangePassword extends AppCompatActivity {
    String TAG="ChangePassword";
    ImageView img_back;
    EditText edt_password,edt_re_password,current_password;
    ImageView iv_eye,c_eye;
    boolean password_visible = false;
    boolean password_visible1 = false;
    RelativeLayout rl_login;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    ArrayList<HashMap<String,String>> Array_email_list;
    ArrayList<HashMap<String,String>> Array_phone_list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        img_back=findViewById(R.id.img_back);
        current_password=findViewById(R.id.current_password);
        edt_password=findViewById(R.id.edt_password);
        edt_re_password=findViewById(R.id.edt_re_password);
        iv_eye=findViewById(R.id.iv_eye);
        c_eye=findViewById(R.id.re_iv_eye);
        rl_login=findViewById(R.id.rl_login);
        Array_email_list = new ArrayList<>();
        Array_phone_list = new ArrayList<>();
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_current_password = current_password.getText().toString();
                String password = edt_password.getText().toString();
                String re_password = edt_re_password.getText().toString();
                if(validateLogin(str_current_password, password,re_password)){
                    login(str_current_password, password,re_password);
                }
            }
        });

        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible) {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.iconseye);

                    password_visible = true;

                } else {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.iconeye);

                    password_visible = false;

                }

                edt_password.setSelection(edt_password.length());
            }
        });
        c_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible1) {

                    edt_re_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    c_eye.setImageResource(R.mipmap.iconseye);

                    password_visible1 = true;

                } else {

                    edt_re_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    c_eye.setImageResource(R.mipmap.iconeye);

                    password_visible1 = false;

                }

                edt_re_password.setSelection(edt_re_password.length());
            }
        });

    }
    private boolean validateLogin(String current_pass, String new_password,String confirm_pass){
        if(current_pass == null || current_pass.trim().length() == 0){
            Toast.makeText(this, "Current password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(new_password == null || new_password.trim().length() == 0){
            Toast.makeText(this, " New Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(confirm_pass == null || confirm_pass.trim().length() == 0){
            Toast.makeText(this, " Confirm Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!(new_password.equals(confirm_pass))){
            Toast.makeText(this, "  Password not match ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void login(final String current_password, final String password,final String re_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+change_password, new Response.Listener<String>() {


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
                        globalClass.setProfil_pic(user_image_url+profile_image);

                        globalClass.setLogin_status(false);
                         preference.clearPrefrence();

                        preference.savePrefrence();

                        Intent intent = new Intent(ChangePassword.this, Login.class);
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

                params.put("current_password", current_password);
                params.put("new_password", password);
                params.put("verify_password", re_password);
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
