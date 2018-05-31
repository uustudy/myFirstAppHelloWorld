package com.example.administrator.helloworld;

/**
 * Created by Administrator on 2018/4/11.
 */
public class Fruit {

    String name;
    int imageId;

    public Fruit(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }


    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
