package com.example.dqddu.ext

import java.text.DecimalFormat

/**
 * Double 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:15 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

private const val GROUPING_SIZE = 3

fun Double.toPrice(): String {
    val pattern = "#,###.00"
    val decimalFormat = DecimalFormat(pattern)
    decimalFormat.groupingSize = GROUPING_SIZE
    return "€" + decimalFormat.format(this)
}
