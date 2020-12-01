package com.example.dqforolivia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyNetWork {
    void test() {

        // tool object
        Retrofit myretrofilt = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // service object and bind function
        MyApi service = myretrofilt.create(MyApi.class);

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

class MyBaseData {
    List<MyData> data;
}

class MyData {
    String name;
    int age;
}
