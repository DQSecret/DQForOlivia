package com.example.dqddu.ext

/**
 * Format 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:07 PM
 * @see <a href="https://betterprogramming.pub/10-useful-kotlin-string-extensions-46772b653f71">文章</a>
 */

private const val DIGITS = 4

/**
 * 输入兑换码时, 需要每四位加一空格, 方便校验
 */
val String.creditCardFormatted: String
    get() {
        val preparedString = replace(" ", "").trim()
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i % DIGITS == 0 && i != 0) {
                result.append(" ")
            }
            result.append(preparedString[i])
        }
        return result.toString()
    }
