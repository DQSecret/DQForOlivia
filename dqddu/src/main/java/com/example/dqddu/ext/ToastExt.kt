package com.example.dqddu.ext

import android.widget.Toast
import com.example.dqddu.base.BaseApp

/**
 * 简单的 Toast 弹出
 *
 * @author DQDana For Olivia
 * @since 4/22/21 4:24 PM
 * @see <a href="无">无</a>
 */

fun CharSequence.toast() =
    Toast.makeText(BaseApp.app.applicationContext, this, Toast.LENGTH_SHORT).show()
