package com.academy.keytone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.academy.keytone.networkService.AppConfig.login;
import static com.academy.keytone.networkService.AppConfig.save_user;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String TAG="Register";
    Spinner spinner;
    TextView tv_register;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    EditText edt_user_id,edt_password,edt_re_password;
    ArrayList<HashMap<String,String>> Array_email_list;
    ArrayList<HashMap<String,String>> Array_phone_list;
    RelativeLayout rl_login;
    ImageView iv_eye,re_iv_eye;
    boolean password_visible = false;
    boolean password_visible1 = false;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                   // "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        spinner=findViewById(R.id.spinner_parent);
        rl_login=findViewById(R.id.rl_login);
        re_iv_eye=findViewById(R.id.re_iv_eye);
        iv_eye=findViewById(R.id.iv_eye);
        edt_password=findViewById(R.id.edt_password);
        edt_user_id=findViewById(R.id.edt_user_id);
        edt_re_password=findViewById(R.id.edt_re_password);
        spinner.setEnabled(false);
        spinner.setFocusable(false);
        spinner.setClickable(false);
        tv_register=findViewById(R.id.tv_register);
        Array_email_list = new ArrayList<>();
        Array_phone_list = new ArrayList<>();
        List<String> categories = new ArrayList<String>();
        categories.add("Guest");
       // categories.add("Parent");
       // categories.add("Teacher");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,  R.layout.spinner_item1, R.id.txt1, categories);

        // Drop down layout style - list view with radio button

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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
        re_iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible1) {

                    edt_re_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    re_iv_eye.setImageResource(R.mipmap.iconseye);

                    password_visible1 = true;

                } else {

                    edt_re_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    re_iv_eye.setImageResource(R.mipmap.iconeye);

                    password_visible1 = false;

                }

                edt_re_password.setSelection(edt_re_password.length());
            }
        });

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_user_id.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String c_password = edt_re_password.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {

                        if (!edt_user_id.getText().toString().isEmpty()) {
                            if (isValidEmail(edt_user_id.getText().toString())) {

                                if (validatePassword()){


                                    if (!edt_password.getText().toString().isEmpty()) {
                                        if (!edt_re_password.getText().toString().isEmpty()) {

                                            if (edt_password.getText().toString().equals(edt_re_password.getText().toString())) {
                                                Register(email, password, c_password);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Confirm password empty", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_LONG).show();
                                    }


                                }/* else {
                                    Toast.makeText(getApplicationContext(), "Password must contain mix of upper and lower case letters as well as digits and one special charecter(8-20)", Toast.LENGTH_LONG).show();
                                }*/

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Insert Proper mail id", Toast.LENGTH_LONG).show();

                            }
                        } else {
                           Toast.makeText(getApplicationContext(), "Email is empty", Toast.LENGTH_LONG).show();
                        }

                }
            }

        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      //  String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
      //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void Register(final String user_email, final String password,String confirm_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+save_user, new Response.Listener<String>() {


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
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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


                        preference.savePrefrence();

                        Intent intent = new Intent(Register.this, Login.class);
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
                params.put("confirm_password", confirm_password);
                params.put("role_id", "4");


                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(password);
        return matcher.matches();
    }
    private boolean validatePassword() {
        String passwordInput = edt_password.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password must contain mix of upper and lower case letters as well as digits and one special charecter(8-20)", Toast.LENGTH_LONG).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Toast.makeText(getApplicationContext(), "Password must contain mix of upper and lower case letters as well as digits and one special charecter(8-20)", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(getApplicationContext(), "Password must contain mix of upper and lower case letters as well as digits and one special charecter(8-20)", Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
