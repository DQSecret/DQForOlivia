package com.example.dqddu.ext

import android.content.Context
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.util.regex.Pattern

/**
 * Pattern 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:01 PM
 * @see <a href="https://betterprogramming.pub/10-useful-kotlin-string-extensions-46772b653f71">文章</a>
 */

/**
 * 验证电子邮件 - 正则
 */
fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * 验证电话号码 - 使用库(libphonenumber-android) - 全球
 * * 国家标识符 - [io.michaelrocks.libphonenumber.android.CountryCodeToRegionCodeMap]
 */
fun String.formatPhoneNumber(context: Context, region: String): String? {
    val phoneNumberKit = PhoneNumberUtil.createInstance(context)
    val number = phoneNumberKit.parse(this, region)
    if (!phoneNumberKit.isValidNumber(number)) {
        return null
    }
    return phoneNumberKit.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
}

/**
 * 验证电话号码 - 使用正则 - 仅国内
 */
fun String.isPhoneNumber(): Boolean {
    val expression = "^1[3-9]\\d{9}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * 包含字母
 */
val String.containsLatinLetter: Boolean
    get() = matches(Regex(".*[A-Za-z].*"))

/**
 * 包含数字
 */
val String.containsDigit: Boolean
    get() = matches(Regex(".*[0-9].*"))

/**
 * 包含[数字|小写字母|大写字母],取反后,可用于判断是否包含特殊字符
 */
val String.isAlphanumeric: Boolean
    get() = matches(Regex("[A-Za-z0-9]*"))

/**
 * 同时包含字母和数字
 */
val String.hasLettersAndDigits: Boolean
    get() = containsLatinLetter && containsDigit
