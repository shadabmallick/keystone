package com.academy.keytone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import static com.academy.keytone.networkService.AppConfig.forgot_password;
import static com.academy.keytone.networkService.AppConfig.login;

public class ForgetPassword extends AppCompatActivity {
    String TAG="Login";

    RelativeLayout rl_login;
    EditText edt_user_id;
    ImageView img_back;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    ArrayList<HashMap<String,String>> Array_email_list;
    ArrayList<HashMap<String,String>> Array_phone_list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        rl_login=findViewById(R.id.rl_login);
        img_back=findViewById(R.id.img_back);
        edt_user_id=findViewById(R.id.edt_user_id);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        Array_email_list = new ArrayList<>();
        Array_phone_list = new ArrayList<>();
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

               String username=edt_user_id.getText().toString().trim();

               if(validateLogin(username)){
                   Forget(username);
                }

            }
        });
    }
    private boolean validateLogin(String username){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void Forget(final String user_email) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+forgot_password, new Response.Listener<String>() {


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
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    Log.d(TAG, "Message: "+message);

                    if(status.equals("1") ) {

                        String  user_id = jobj.get("user_id").getAsString().replaceAll("\"", "");
                        String  otp = jobj.get("otp").getAsString().replaceAll("\"", "");

                        Intent intent = new Intent(ForgetPassword.this, ResetPassword.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("otp",otp);
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


                params.put("email", user_email);



                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
