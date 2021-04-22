@file:Suppress("TooGenericExceptionCaught")

package com.example.dqddu.ext

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import com.thefinestartist.finestwebview.FinestWebView

/**
 * Uri 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:52 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

val String.asUri: Uri?
    get() = try {
        if (URLUtil.isValidUrl(this)) {
            Uri.parse(this)
        } else {
            null
        }
    } catch (e: NullPointerException) {
        e.printStackTrace()
        null
    }

fun Uri.open(context: Context): Boolean =
    if (this.scheme == "http" || this.scheme == "https") {
        openInside(context)
        true
    } else {
        openOutside(context)
    }

fun Uri.openInside(context: Context) =
    FinestWebView.Builder(context).show(this.toString())

fun Uri.openOutside(context: Context): Boolean =
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, this)
        context.startActivity(browserIntent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
