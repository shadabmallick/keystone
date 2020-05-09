package com.academy.keytone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.academy.keytone.R;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;

public class Splash extends AppCompatActivity {
    String TAG="Splash";

    GlobalClass globalClass;
    ProgressDialog pd;
    Shared_Preference prefrence;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
/*

        UpdateChecker checker = new UpdateChecker(this);
        // If you are in a Activity or a FragmentActivity

        checker.start();
*/


        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Splash.this);
        prefrence.loadPrefrence();

        Log.d(TAG, "onCreate: "+globalClass.getLogin_status());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if(globalClass.getLogin_status().equals(true)){
                    Intent intent = new Intent(Splash.this, HomeScreen.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);
    }


}