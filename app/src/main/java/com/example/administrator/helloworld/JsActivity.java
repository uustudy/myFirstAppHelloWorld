package com.example.administrator.helloworld;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

/**
 * SuppressLint一定要加上去！！！
 * 低版本可能没问题，高版本JS铁定调不了Android里面的方法
 */
@SuppressLint("SetJavaScriptEnabled")
public class JsActivity extends Activity {
    private Button button1,button2;
    private WebView mWebView;
    private MyWebViewClient WVClient;
    private WebSettings webSettings;
    private MyWebChromeClient chromeClient;
    //封装接收js调用Android的方法类
    private JSObject jsobject;
    //异步请求
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        init();
        initView();
        setButton();
    }
    private void setButton() {
        //无参调用
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:showNoMessage()");
                    }
                });*/

                mWebView.loadUrl("javascript:showNoMessage()");
            }
        });
        //有参调用
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:showMessage('顺带给JS传个参数')");
                    }
                });
            }
        });
    }
    private void init() {
        mWebView = (WebView) findViewById(R.id.webview);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        WVClient = new MyWebViewClient();
        chromeClient = new MyWebChromeClient();
        jsobject = new JSObject(JsActivity.this);
    }
    private void initView() {
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSavePassword(false);
        //支持多种分辨率，需要js网页支持
        webSettings.setUserAgentString("mac os");
        webSettings.setDefaultTextEncodingName("utf-8");
        //显示本地js网页

        //mWebView.setBackgroundResource(R.drawable.myload);//设置背景图片
        //mWebView.setBackgroundColor(android.R.color.black);

        mWebView.setBackgroundColor(0);//先设置背景色为transparent
        mWebView.setBackgroundResource(R.drawable.loading);//然后设置背景图片

        mWebView.loadUrl("file:///android_asset/test.html");
        mWebView.setWebViewClient(WVClient);
        mWebView.setWebChromeClient(chromeClient);
        //注意第二个参数android，这个是JS网页调用Android方法的一个类似ID的东西
        mWebView.addJavascriptInterface(jsobject, "android");

        //第一个参数为this，则为当前活动
        //mWebView.addJavascriptInterface(this, "android");
    }


    @JavascriptInterface
    public void mymethod(String str,String str1) {
        Toast.makeText(this, (str + "_" + str1), Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent();
        intent.putExtra("name", str);
        intent.putExtra("pass", str);
        intent.setClass(PhoneGap2Activity.this, TestActivity.class);
        startActivity(intent);*/
    }
}
