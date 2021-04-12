package com.example.dqddu.ext

import java.util.*

/**
 * Int 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 5:59 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

fun Int.centsToDollars(): Double = this.toDouble() / 100.0

fun Int.centsToDollarsFormat(currency: String): String {
    val dollars = this / 100
    val cents = this % 100
    return String.format(Locale.US, "%s%d.%02d", currency, dollars, cents)
}
