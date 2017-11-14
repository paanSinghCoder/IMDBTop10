package com.topten.grv.imdbtop10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ArrayList<String> urlList;
    int position;
    private Toolbar toolbar;
    private AVLoadingIndicatorView loadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.loadingView);

        //Toolbar setup>>>START
        toolbar = (Toolbar) findViewById(R.id.toolbar_webview_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //Toolbar setup>>>END

        //web view setup>>>START
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        urlList = getIntent().getStringArrayListExtra("url_list");//Recieving imdb url for webview
        position = getIntent().getIntExtra("position",1);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                loadingIndicatorView.hide();
            }
        });
        webView.loadUrl(urlList.get(position));
        //web view setup>>>START
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_webview_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.previous)
        {
            if(position<1)
            {
                Toast toastPrev = Toasty.warning(WebViewActivity.this, "This is the first Movie in the list", Toast.LENGTH_SHORT, true);
                //toastPrev.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                toastPrev.show();
            }else{

                position = position - 1;
                webView.loadUrl(urlList.get(position));
                loadingIndicatorView.hide();//both hide and show are called because user may
                loadingIndicatorView.show();// click next/previous when page is loading so this will hide and again show the loadingview
            }

            return true;
        }
        else if(item.getItemId()==R.id.next)
        {
            if(position==(urlList.size()-1))
            {
                Toast toastPrev = Toasty.warning(WebViewActivity.this, "This is the last Movie in the list", Toast.LENGTH_SHORT, true);
                //toastPrev.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                toastPrev.show();

            }else {

                position = position + 1;
                webView.loadUrl(urlList.get(position));
                loadingIndicatorView.hide();//both hide and show are called because user may
                loadingIndicatorView.show();// click next/previous when page is loading so this will hide and again show the loadingview
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
