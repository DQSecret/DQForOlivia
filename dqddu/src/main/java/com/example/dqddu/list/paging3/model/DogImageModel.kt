package com.example.dqddu.list.paging3.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogImageModel(
    val id: String,
    val url: String
)