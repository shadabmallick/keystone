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
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.academy.keytone.networkService.AppConfig.forgot_password;
import static com.academy.keytone.networkService.AppConfig.update_password;

public class ResetPassword extends AppCompatActivity {
    String TAG="Reset";
    RelativeLayout rl_login;
    ImageView img_back;
    EditText edt_password,edt_re_password,edt_user_id;
    ImageView iv_eye,c_eye;
    boolean password_visible = false;
    boolean password_visible1 = false;
    String user_id,otp;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        img_back=findViewById(R.id.img_back);
        rl_login=findViewById(R.id.rl_login);
        edt_password=findViewById(R.id.edt_password);
        edt_re_password=findViewById(R.id.edt_re_password);
        edt_user_id=findViewById(R.id.edt_user_id);
        iv_eye=findViewById(R.id.iv_eye);
        c_eye=findViewById(R.id.re_iv_eye);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        otp = intent.getStringExtra("otp");
        edt_user_id.setText(otp);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String password = edt_password.getText().toString().trim();
                String c_password = edt_re_password.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {


                            if (!edt_password.getText().toString().isEmpty()) {
                                if (!edt_re_password.getText().toString().isEmpty()) {

                                    if (edt_password.getText().toString().equals(edt_re_password.getText().toString())) {
                                        Forget(password,c_password);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Confirm password empty", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Password Empty",Toast.LENGTH_LONG).show();
                            }





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
    private void Forget(final String password,final String c_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+update_password, new Response.Listener<String>() {


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


                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ResetPassword.this, Login.class);

                        startActivity(intent);




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


                params.put("user_id", user_id);
                params.put("otp", otp);
                params.put("new_password", password);
                params.put("verify_password", c_password);



                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
