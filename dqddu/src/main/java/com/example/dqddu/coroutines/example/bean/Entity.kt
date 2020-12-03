package com.example.dqddu.coroutines.example.bean

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.parcelize.Parcelize

/**
 * 网络返回的基础数据结构
 *
 * @author DQDana For Olivia
 * @since 2020/12/3 11:38 AM
 */

@JsonClass(generateAdapter = true)
data class BaseData<T>(
    val data: T?,
    val errorCode: Int = 0,
    val errorMsg: String = ""
)

@JsonClass(generateAdapter = true)
data class BaseListData<T>(
    val data: List<T> = emptyList(),
    val errorCode: Int = 0,
    val errorMsg: String = ""
)

@JsonClass(generateAdapter = true)
@Parcelize // 这个是为了序列化, 方便利用 Intent|Bundle 传值, 但是不能作用在<T>上, 也挺尴尬的
data class CommonlyUsedWebSite(
    val category: String = "",
    val icon: String = "",
    val id: Long = -1,
    val link: String = "",
    val name: String = "",
    val order: Long = -1,
    val visible: Int = 1,
) : Parcelable

typealias CommonlyUsedWebSiteListRepo = BaseListData<CommonlyUsedWebSite>

fun main() {
    val moshi = Moshi.Builder().build()
    moshi.adapter<BaseData<List<CommonlyUsedWebSite>>>(
        Types.newParameterizedType(
            BaseData::class.java,
            CommonlyUsedWebSite::class.java
        )
    ).fromJson("{}").also {
        println(it)
    }
    moshi.adapter<CommonlyUsedWebSiteListRepo>(
        Types.newParameterizedType(
            BaseListData::class.java,
            CommonlyUsedWebSite::class.java
        )
    ).fromJson("{}").also {
        println(it)
    }
}