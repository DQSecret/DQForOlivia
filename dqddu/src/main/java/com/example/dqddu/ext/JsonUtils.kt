package com.example.dqddu.ext

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Json 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:04 PM
 * @see <a href="https://betterprogramming.pub/10-useful-kotlin-string-extensions-46772b653f71">文章</a>
 */

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
