package com.onedaydent.onedaydent.Notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.onedaydent.onedaydent.Common.WebViewClientClass;
import com.onedaydent.onedaydent.R;
import com.onedaydent.onedaydent.Welcome.WelcomeActivity;

public class UrlLoadActivity extends AppCompatActivity {

    private WebViewClientClass wvcc;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_load);

        Intent intent = getIntent();
        webView = findViewById(R.id.url_webview);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        wvcc = new WebViewClientClass(this, webView, progressBar);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(wvcc);

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                finish();
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                final WebSettings settings = view.getSettings();
                settings.setDomStorageEnabled(true);
                settings.setJavaScriptEnabled(true);
                settings.setAllowFileAccess(true);
                settings.setAllowContentAccess(true);
                view.setWebChromeClient(this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();
                return false;
            }
        });
        String url =  intent.getStringExtra("URL");
        Log.d("TAG", "onCreate: " + url);
        webView.setWebViewClient(wvcc);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}
