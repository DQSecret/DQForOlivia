package com.example.dqddu.main

import android.os.Bundle
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.base.startPageByScheme
import com.example.dqddu.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun initBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 跳转到屏幕截图检测
        binding.btnScreenshots.setOnClickListener {
            startPageByScheme("screenshots")
        }
        // 跳转到网络示例
        binding.btnNetworkExample.setOnClickListener {
            startPageByScheme("network-example")
        }
    }
}