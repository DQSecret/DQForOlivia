package com.example.dqddu.ext

import android.graphics.Color

/**
 * Color 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:06 PM
 * @see <a href="https://betterprogramming.pub/10-useful-kotlin-string-extensions-46772b653f71">文章</a>
 */

val String.asColor: Int?
    get() = try {
        Color.parseColor(this)
    } catch (e: java.lang.IllegalArgumentException) {
        null
    }
