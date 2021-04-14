package com.example.dqddu.ext

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Toast 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/13/21 7:59 PM
 */

fun AppCompatActivity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun View.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
