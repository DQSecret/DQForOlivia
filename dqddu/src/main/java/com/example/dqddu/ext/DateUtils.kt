@file:Suppress("NoWildcardImports")

package com.example.dqddu.ext

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Date 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:10 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

private const val UNIT: Long = 1000L

fun Int.toDate(): Date = Date(this.toLong() * UNIT)

val Int.asDate: Date
    get() = Date(this.toLong() * UNIT)

fun String.toDate(format: String): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.US)
    return try {
        dateFormatter.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun Date.toString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale.US)
    return dateFormatter.format(this)
}
