package com.example.dqddu.ext

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dqddu.base.BaseApp

/**
 * Toast 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/13/21 7:59 PM
 */

fun AppCompatActivity.toast(msg: String?) {
    toast(this, msg)
}

fun Fragment.toast(msg: String?) {
    toast(requireContext(), msg)
}

fun View.toast(msg: String?) {
    toast(context, msg)
}

fun toast(context: Context = BaseApp.app.applicationContext, msg: String?) {
    Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()
}
