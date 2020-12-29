package com.example.dqddu.ext

import android.content.Context
import android.content.pm.PackageManager

/**
 * Version 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:19 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

val Context.versionName: String?
    get() = try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        pInfo?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }

val Context.versionCode: Long?
    get() = try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            pInfo?.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            pInfo?.versionCode?.toLong()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
