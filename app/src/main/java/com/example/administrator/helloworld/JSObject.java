package com.example.administrator.helloworld;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
/**
 * JS调用android的方法
 * @JavascriptInterface仍然必不可少
 */
public class JSObject {
    private Context context;
    public JSObject(Context context){
        this.context = context;
    }
    //js调用无参方法
    @JavascriptInterface
    public void callNull(){
        Toast.makeText(context, "JsCallAndroid", Toast.LENGTH_SHORT).show();
    }
    //js调用有参方法
    @JavascriptInterface
    public void callMessage(String data){
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", "World!");
        context.startActivity(intent);
    }
    //js调用有参方法，参数类型：JSON
    @JavascriptInterface
    public void callJson(String data) throws JSONException{
        JSONArray jsonArray = new JSONArray(data);
        Toast.makeText(context, jsonArray.toString(), Toast.LENGTH_SHORT).show();
    }
    //js调用有参方法，参数类型：JSON，获取电话号码拨打
    @JavascriptInterface
    public void callPhone(String data){
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data)));
    }

    @JavascriptInterface
    public void mymethod(String str,String str1) {
        Toast.makeText(context, str + "_" + str1, Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent();
        intent.putExtra("name", str);
        intent.putExtra("pass", str);
        intent.setClass(PhoneGap2Activity.this, TestActivity.class);
        startActivity(intent);*/

        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("param1", str);
        intent.putExtra("param2", "World!");
        context.startActivity(intent);
    }
}