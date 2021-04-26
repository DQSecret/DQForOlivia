package com.example.dqddu.list.paging3.repository.remote

import com.example.dqddu.list.paging3.model.DogImageModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 狗狗的图片
 *
 * url = https://api.thecatapi.com/
 *
 * @author DQDana For Olivia
 * @since 4/25/21 3:44 PM
 */
interface DogApiService {

    @GET("v1/images/search")
    suspend fun getDogImages(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("order") order: String = "ASC",
    ): List<DogImageModel>
}
