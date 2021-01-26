package com.example.dqddu.coroutines.example.api

import com.example.dqddu.coroutines.example.bean.CommonlyUsedWebSiteListRepo
import retrofit2.http.GET

/**
 * api 定义
 *
 * @author DQDana For Olivia
 * @since 2020/12/3 4:52 PM
 */
interface ApiService {

    @GET("/friend/json")
    suspend fun getCommonlyUsedWebSiteListRepoAsync(): CommonlyUsedWebSiteListRepo
}
