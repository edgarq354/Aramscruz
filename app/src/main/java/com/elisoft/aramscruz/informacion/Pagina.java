package com.elisoft.aramscruz.informacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.elisoft.aramscruz.R;

public class Pagina extends AppCompatActivity {

    String pagina="",titulo="";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView myWebView = (WebView) findViewById(R.id.wv_condiciones);


        try {
            Bundle bundle= getIntent().getExtras();
            titulo=bundle.getString("titulo","");
            pagina=bundle.getString("url","");



            this.setTitle(titulo);

            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new WebViewClient());
            myWebView.loadUrl(pagina);
        } catch (Exception e)
        {

        }


    }

}

