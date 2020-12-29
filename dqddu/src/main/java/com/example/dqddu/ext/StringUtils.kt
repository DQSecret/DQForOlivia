package com.example.dqddu.ext

import java.security.MessageDigest

/**
 * String 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 11:39 AM
 * @see <a href="https://betterprogramming.pub/10-useful-kotlin-string-extensions-46772b653f71">文章</a>
 */

val String.md5: String
    get() {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

val String.sha1: String
    get() {
        val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

val String.isIntegerNumber: Boolean
    get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
    get() = toDoubleOrNull() != null
