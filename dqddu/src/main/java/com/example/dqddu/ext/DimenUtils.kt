package com.example.dqddu.ext

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

/**
 * Dimen 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/13/21 7:46 PM
 * @see <a href="https://kaixue.io/kotlin-extensions/">文章</a>
 */

val Number.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()
