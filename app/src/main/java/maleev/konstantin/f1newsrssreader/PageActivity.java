package maleev.konstantin.f1newsrssreader;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ShareActionProvider;

public class PageActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        WebView webView= (WebView) findViewById(R.id.webViewPage);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        Intent intent=getIntent();
        String url=intent.getStringExtra("url");

        if(url==null)
            return;

        setUpWebViewDefaults(webView);
        setUseTextAutoSize(webView,true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.TEXT_AUTOSIZING);
//        }


        try {
            webView.loadUrl(url);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }
    }


    // Change the layout algorithm used in the WebView
    private void setUseTextAutoSize(WebView mWebView, boolean useAlgorithm) {
        WebSettings settings = mWebView.getSettings();

        LayoutAlgorithm layoutAlgorithm = LayoutAlgorithm.NORMAL;
        if(useAlgorithm) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                layoutAlgorithm = LayoutAlgorithm.TEXT_AUTOSIZING;
            }
        }

        settings.setLayoutAlgorithm(layoutAlgorithm);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
