package com.example.dqforolivia;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyNetWork {
    void test(){
        // tool
        Retrofit myretrofilt = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi service = myretrofilt.create(MyApi.class);

    }


}
