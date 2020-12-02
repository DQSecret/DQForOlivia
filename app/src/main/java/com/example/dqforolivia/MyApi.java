package com.example.dqforolivia;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    // path
    @GET("friend/json")
    Call<MyBaseData> getArticleList();
}
