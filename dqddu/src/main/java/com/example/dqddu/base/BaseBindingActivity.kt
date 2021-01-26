package com.example.dqddu.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * BaseBindingActivity : 含有 ViewBinding 的基础 Activity
 *
 * @param T ViewBinding 的具体类
 *
 * @author DQDana For Olivia
 * @since 2020/11/26 2:28 PM
 */
abstract class BaseBindingActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding()
        setContentView(binding.root)
    }

    abstract fun initBinding(): T
}
