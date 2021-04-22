@file:Suppress("NoWildcardImports")

package com.example.dqddu.ext

import java.util.*

/**
 * Int 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 5:59 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

private const val UNIT = 100

fun Int.centsToDollars(): Double = this.toDouble() / UNIT

fun Int.centsToDollarsFormat(currency: String): String {
    val dollars = this / UNIT
    val cents = this % UNIT
    return String.format(Locale.US, "%s%d.%02d", currency, dollars, cents)
}
