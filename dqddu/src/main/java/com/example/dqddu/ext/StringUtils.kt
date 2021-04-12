package com.example.dqddu.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.regex.Pattern

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
    if (!phoneNumberKit.isValidNumber(number))
        return null

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

val String.isIntegerNumber: Boolean
    get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
    get() = toDoubleOrNull() != null


@SuppressLint("ApplySharedPref")
fun String.save(
    applicationContext: Context,
    value: Map<String, Any>,
    clear: Boolean = false,
    now: Boolean = false
) {
    val sp = applicationContext.getSharedPreferences(this, Context.MODE_PRIVATE).edit()
    if (clear)
        sp.clear()
    value.keys.forEach { key ->
        val v = value[key]
        if (v != null) {
            when (v) {
                is String -> sp.putString(key, v)
                is Float -> sp.putFloat(key, v)
                is Long -> sp.putLong(key, v)
                is Int -> sp.putInt(key, v)
                is Boolean -> sp.putBoolean(key, v)
            }
        }
    }
    if (now)
        sp.commit()
    else
        sp.apply()
}

fun String.load(applicationContext: Context): Map<String, Any> {
    val sp = applicationContext.getSharedPreferences(this, Context.MODE_PRIVATE)
    val keys = sp.all.keys
    val result = hashMapOf<String, Any>()
    keys.map { key ->
        val v = sp.all[key]
        if (v != null)
            result[key] = v
    }
    return result
}

val String.jsonObject: JSONObject?
    get() = try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }

val String.jsonArray: JSONArray?
    get() = try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }

/**
 * 获得Path的最后一部分
 */
val String.lastPathComponent: String
    get() {
        var path = this
        if (path.endsWith("/"))
            path = path.substring(0, path.length - 1)
        var index = path.lastIndexOf('/')
        if (index < 0) {
            if (path.endsWith("\\"))
                path = path.substring(0, path.length - 1)
            index = path.lastIndexOf('\\')
            if (index < 0)
                return path
        }
        return path.substring(index + 1)
    }


val String.asColor: Int?
    get() = try {
        Color.parseColor(this)
    } catch (e: java.lang.IllegalArgumentException) {
        null
    }

/**
 * 输入兑换码时, 需要每四位加一空格, 方便校验
 */
val String.creditCardFormatted: String
    get() {
        val preparedString = replace(" ", "").trim()
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ")
            }
            result.append(preparedString[i])
        }
        return result.toString()
    }

// region Json相关 - 发生异常时,默认为null

fun JSONObject.getIntOrNull(name: String): Int? =
    try {
        getInt(name)
    } catch (e: JSONException) {
        val strValue = getStringOrNull(name)
        strValue?.toIntOrNull()
    }

fun JSONObject.getDoubleOrNull(name: String): Double? =
    try {
        getDouble(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getLongOrNull(name: String): Long? =
    try {
        getLong(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getStringOrNull(name: String): String? =
    try {
        getString(name).trim()
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getBooleanOrNull(name: String): Boolean? =
    try {
        getBoolean(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getObjectOrNull(name: String): JSONObject? =
    try {
        getJSONObject(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getArrayOrNull(name: String): JSONArray? =
    try {
        getJSONArray(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getArrayOrEmpty(name: String): JSONArray =
    try {
        getJSONArray(name)
    } catch (e: JSONException) {
        JSONArray()
    }
