package com.academy.keytone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.academy.keytone.R;
import com.academy.keytone.util.GlobalClass;
import com.academy.keytone.util.Shared_Preference;

public class PageLink extends AppCompatActivity {
    String TAG = "Splash";

    GlobalClass globalClass;
    ProgressDialog pd;
    Shared_Preference prefrence;
    WebView webView_link;
    String link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_link);
        webView_link=findViewById(R.id.webview_link);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        webView_link.getSettings().setJavaScriptEnabled(true);
        webView_link.loadUrl(link);
    }
}