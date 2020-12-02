package com.example.dqforolivia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    void test() {

        // tool builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // service bind
        Api service = retrofit.create(Api.class);

        // repos: 返回对象
        Call<BaseData> repos = service.getArticleList();

        // 进队: 异步开启线程
        repos.enqueue(new Callback<BaseData>() {

            @Override
            public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                String category = response.body().data.get(0).category;
                System.out.println(category);
            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}


class BaseData {
    List<Datum> data;
}

class Datum {
    String category;
    String icon;
    long id;
    String link;
}
//
//class Women {
//    String name;
//}
//
//class DataDatum extends BaseData<Datum> {
//
//}
//
//class DataWomen extends BaseData<Women> {
//
//    void test() {
//        int[] i = new int[]{1, 2, 3, 4};
//        List<Integer> l = new ArrayList<>();
//        for (int j = 0; j < i.length; j++) {
//            l.add(new Integer(i[j]));
//        }
//    }
//}
