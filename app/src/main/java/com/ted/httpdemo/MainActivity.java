package com.ted.httpdemo;

import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView mTextv;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mTextv = (TextView)findViewById(R.id.textv);
        final String url = "https://www.baidu.com/";

        //connect(url);
        webview(url);

    }

    private void webview(String url) {
        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // { @ WebView 返回上一页
        Log.d("tui", "onBackPressed");
        if(mWebView.canGoBack()){
            Log.d("tui", "WebView canGoback");
            mWebView.goBack();
        }else{
            System.exit(0);
        }
        // @ }

    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        *//*Log.d("tui", "onKeyDown, key is " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if(mWebView.canGoBack()){
                Log.d("tui", "WebView canGoback");
                mWebView.goBack();
            }else{
                System.exit(0);
            }
        }*//*

        return true;//super.onKeyDown(keyCode, event);
    }*/

    private void connect(final String url) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                URL pathUrl;

                try{
                    pathUrl = new URL(url);
                    HttpURLConnection urlConnect = (HttpURLConnection)pathUrl.openConnection();
                    urlConnect.setConnectTimeout(10000);
                    InputStreamReader in = new InputStreamReader(urlConnect.getInputStream());
                    BufferedReader buffer = new BufferedReader(in);
                    String inputline = null;
                    String resultData = null;
                    while ((inputline=buffer.readLine()) != null){
                        resultData += inputline;
                    }
                    Message msg = myHandler.obtainMessage();
                    msg.obj = resultData;

                    myHandler.sendMessage(msg);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        },1000);
    }

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            //mTextv.setText(data);
        }
    };
}
