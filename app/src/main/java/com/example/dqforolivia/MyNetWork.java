package com.example.dqforolivia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyNetWork {
    // start send request
    void startRequest() {

        // Tool object
        Retrofit myretrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Service object and bind function
        MyApi service = myretrofit.create(MyApi.class);

        // Response object
        Call<MyBaseData> res = service.getArticleList();

        res.enqueue(new Callback<MyBaseData>() {
            // success
            @Override
            public void onResponse(Call<MyBaseData> call, Response<MyBaseData> response) {
                String names = response.body().data.get(0).name;
            }

            // failed
            @Override
            public void onFailure(Call<MyBaseData> call, Throwable t) {

            }
        });

    }


}

// Base data
class MyBaseData {
    List<MyData> data;
}

// data structure
class MyData {
    String name;
    int age;
}
