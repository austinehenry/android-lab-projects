package com.example.webapp;

import android.os.Build;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        wv = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setSupportZoom(false);

        // Offline caching support
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE); // Always load from the network
        wv.clearCache(true); // Clear any existing cache



        wv.setVerticalScrollBarEnabled(true);
        wv.setHorizontalScrollBarEnabled(false);
        wv.setScrollbarFadingEnabled(true); // Can be true or false, doesn't affect overscroll
        wv.setOverScrollMode(WebView.OVER_SCROLL_NEVER); // ← THIS disables the white glow overscroll


        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    //ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int nightModeFlags = getResources().getConfiguration().uiMode &
                    android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }

        swipeRefreshLayout.setOnRefreshListener(() -> wv.reload());

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    Toast.makeText(MainActivity.this, "Failed to load page", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        wv.setWebChromeClient(new WebChromeClient());
        wv.loadUrl("https://newshuntonline.com/");
    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
