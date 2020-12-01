package com.example.dqforolivia;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    // "https://www.wanandroid.com/friend/json"
    @GET("friend/json")
    Call<BaseData> getArticleList();
}
