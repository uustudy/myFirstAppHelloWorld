package com.example.administrator.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends BaseActivity {

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };

    List<Fruit> fruitList = new ArrayList<Fruit>();
    List<String> contactsList = new ArrayList<String>();

    public void initFruit(){
        Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
        fruitList.add(banana);
        Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon);

        Fruit watermelon1 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon1);
        Fruit watermelon2 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon2);
        Fruit watermelon3 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon3);
        Fruit watermelon4 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon4);
        Fruit watermelon5 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon5);
        Fruit watermelon6 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon6);
        Fruit watermelon7 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon7);
        Fruit watermelon8 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon8);
        Fruit watermelon9 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon9);
        Fruit watermelon10 = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon10);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent intent = getIntent();
        String param1 = intent.getStringExtra("p1");
        String param2 =  intent.getStringExtra("p2");
        Toast.makeText(this, param1 + " " + param2, Toast.LENGTH_SHORT).show();


        /*ArrayAdapter<String> adpt = new ArrayAdapter<String>(ListViewActivity.this,android.R.layout.simple_list_item_1,contactsList);
        ListView ls = (ListView)findViewById(R.id.list_view);
        ls.setAdapter(adpt);*/


        initFruit();
        FruitAdapter adpt = new FruitAdapter(this,R.layout.fruit_item,fruitList);
        ListView ls = (ListView)findViewById(R.id.list_view);
        ls.setAdapter(adpt);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit ft = fruitList.get(position);
                Toast.makeText(ListViewActivity.this, ft.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        /*Button listViewBtn = (Button) findViewById(R.id.listViewBtn);
        listViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("p1", "Hello");
                intent.putExtra("p2", "SecondActivity !");
                setResult(RESULT_OK, intent);
                finish();
            }
        });*/


        //第一.你需要导入最新的android-support-v4.jar包
        //第二，检测用户是否已经授权同意过该危险权限，返回true说明还没有授权或者已经拒绝，返回false说明用户已经同意了该危险权限，可以继续执行需要该权限的操作
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            //第三.接着第二步返回true的情况，进行用户未授权或拒绝情况的处理
            //返回true表明用户之前已经选择过拒绝授权了；若是返回false，则表明我们还有戏，用户还没有选择过是否授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "deny for what???", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "show the request popupwindow", Toast.LENGTH_SHORT).show();

                //第四.通过调用系统自带的弹窗询问用户是否授权
                //这时一个异步的方法，TAG_PERMISSION是我们自己定义的一个int类型常量（requestCode），用于标记我们当前的方法。调用此方法，系统会弹出一个dialog，dialog的样式我们是无法修改的
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},1);
            }
        } else {
            Toast.makeText(this, "agreed", Toast.LENGTH_SHORT).show();
            readContacts();
        }

    }




    private void readContacts() {
        Cursor cursor = null;
        try {
            // 查询联系人数据
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                // 获取联系人姓名
                String displayName = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                // 获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(displayName + "\n" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    //第五.重写请求授权的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "allow", Toast.LENGTH_SHORT).show();
                    readContacts();
                } else {
                    Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
