package com.example.dqddu.ext

import android.location.Location

/**
 * Location 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:17 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

fun String.toLocation(provider: String): Location? {
    val components = this.split(",")
    if (components.size != 2) return null
    val lat = components[0].toDoubleOrNull() ?: return null
    val lng = components[1].toDoubleOrNull() ?: return null
    val location = Location(provider)
    location.latitude = lat
    location.longitude = lng
    return location
}
