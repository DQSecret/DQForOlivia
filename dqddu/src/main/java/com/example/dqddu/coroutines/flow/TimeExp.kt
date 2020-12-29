@file:Suppress("NoWildcardImports")

package com.example.dqddu.coroutines.flow

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间格式化的扩展类
 * 目前仅为测试时,打印log使用
 *
 * @author DQDana For Olivia
 * @since 12/30/20 2:31 PM
 */
class TimeExp private constructor() {
    companion object {
        val now: String
            get() = SimpleDateFormat("yyyy-MM-d HH:mm:ss.SSS", Locale.CHINA).format(Date())
    }
}

fun Any.log(
    hasThreadInfo: Boolean = false,
    prefix: CharSequence = "",
    postfix: CharSequence = ""
) {
    val msg = if (hasThreadInfo) {
        "$prefix(${Thread.currentThread().name}) -> $this$postfix"
    } else {
        "$prefix$this$postfix"
    }
    println(msg)
}

fun Any.logNow(
    hasThreadInfo: Boolean = false,
    prefix: CharSequence = "",
    postfix: CharSequence = ""
) {
    val msg = if (hasThreadInfo) {
        "$prefix(${Thread.currentThread().name})${TimeExp.now} -> $this$postfix"
    } else {
        "$prefix${TimeExp.now} -> $this$postfix"
    }
    println(msg)
}
