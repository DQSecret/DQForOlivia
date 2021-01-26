package com.example.dqddu.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import com.example.dqddu.R

/**
 * Scheme 相关的扩展方法
 *
 * @author DQDana For Olivia
 * @since 2020/11/26 4:21 PM
 */

val DEFAULT_SCHEME = BaseApp.app.getString(R.string.intent_scheme)
val DEFAULT_HOST = BaseApp.app.getString(R.string.intent_host)

fun Context.startPageByUrl(url: String) =
    startPageByUri(url.toUri())

fun Context.startPageByScheme(path: String, vararg params: Pair<String, Any>) =
    startPageByUri(createUri(path, *params))

fun Context.startPageByUri(uri: Uri) =
    Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
    }.also {
        startActivity(it)
    }

private fun createUri(path: String, vararg params: Pair<String, Any>): Uri =
    Uri.Builder().scheme(DEFAULT_SCHEME).authority(DEFAULT_HOST)
        .appendEncodedPath(path)
        .apply {
            for ((k, v) in params) {
                appendQueryParameter(k, v.toString())
            }
        }
        .build()
