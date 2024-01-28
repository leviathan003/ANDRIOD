package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HealthArticleReadActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article_read);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.btnBG));

        webView = findViewById(R.id.articleDisplay);
        webView.setVisibility(View.INVISIBLE);
        loader = findViewById(R.id.loader);
        loader.setIndeterminateTintList(ColorStateList.valueOf(getColor(R.color.btnBG)));

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                loader.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}