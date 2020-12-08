package com.example.dqddu.coroutines.example.moshi

import androidx.annotation.Nullable
import com.squareup.moshi.*

/**
 * moshi 序列化库的测试
 *
 * @author DQDana For Olivia
 * @since 2020/12/3 11:38 AM
 */

@JsonClass(generateAdapter = true)
data class FullData(val id: Int, val name: String)

@JsonClass(generateAdapter = true)
data class DefaultData(
    val id: Int = 1006,
    @NullToEmptyString
    val name: String = "DQ",
)

class MoshiTest {

    companion object {
        const val DATA_FULL = """{"id":1,"name":"DQ"}"""
        const val DATA_FULL_EMPTY = """{}"""
        const val DATA_FULL_NULL = """{"id":null,"name":null}"""
        const val DEFAULT_DATA = """{"id":1,"name":"DQ"}"""
        const val DEFAULT_DATA_EMPTY = """{}"""
        const val DEFAULT_DATA_NULL = """{"name":null}"""
    }

    private val moshi = Moshi.Builder()
        .add(NullToEmptyStringAdapter())
        .build()

    fun testFullData() {
        val adapter = moshi.adapter(FullData::class.java)
        // deserialize json
        adapter.fromJson(DATA_FULL).also {
            println(it.toString())
        }
        // 会报错
        /*adapter.fromJson(DATA_FULL_EMPTY).also {
            println(it.toString())
        }*/
        // 会报错
        /*adapter.fromJson(DATA_FULL_NULL).also {
            println(it.toString())
        }*/

        // serialize json
        adapter.toJson(FullData(1, "DQ")).also {
            println(it.toString())
        }
        // 编译不过去
        /*adapter.toJson(FullData()).also {
            println(it.toString())
        }*/
        // 编译不过去
        /*adapter.toJson(FullData(null, null)).also {
            println(it.toString())
        }*/
    }

    fun testDefaultValueData() {
        val adapter = moshi.adapter(DefaultData::class.java).nullSafe()
        // deserialize json
        adapter.fromJson(DEFAULT_DATA).also {
            println(it.toString())
        }
        adapter.fromJson(DEFAULT_DATA_EMPTY).also {
            println(it.toString())
        }
        adapter.fromJson(DEFAULT_DATA_NULL).also {
            println(it.toString())
        }
        // serialize json
//        adapter.toJson(DefaultData()).also {
//            println(it.toString())
//        }
    }
}

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyString

class NullToEmptyStringAdapter {
    @ToJson
    fun toJson(@NullToEmptyString value: String?): String? {
        return value
    }

    @FromJson
    @NullToEmptyString
    fun fromJson(@Nullable data: String?): String {
        return data ?: ""
    }
}

fun main() {
    /*MoshiTest().testFullData()*/
    MoshiTest().testDefaultValueData()

    @Suppress("UNUSED_VARIABLE") val mTestResult = """
        1. 如果使用 [@JsonClass(generateAdapter = true)] 那么自定义适配器将没有作用
           PS: 但是不使用, 就会使用反射, 增加包大小, 以及解析耗时问题
        2. 关于默认值:
           2.1 如果没有默认值, 即认为, 服务器一定会返回该值, 如果没有返回, 报错!!!
           2.2 如果有默认值, 服务器没有返回的话, 取默认值使用
               PS: 如果返回 null 报错!!!
        3. 关于是否可以为空(?)
           3.1 假如设置了 String?
               无论是否返回 or 返回为 null 都不报错.
        
        综上所述:
            后台:
                基础类型, 必须返回值, 不准返回 null
                对象的话, 可以返回 null, 但建议不返回该字段
                列表的话, 默认返回 [] 即可
            客户端:
                基础类型, 必须给上默认值
                对象, 可为空, 并给上默认值 eg: val obj: T? = null
                类表的话, 默认 emptyList()
    """
}