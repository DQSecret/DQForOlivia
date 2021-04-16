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
        // 跳转到MotionLayout示例
        binding.btnMotionLayoutExample.setOnClickListener {
            startPageByScheme("motion-layout-example")
        }
        // 跳转到MotionLayout示例2
        binding.btnMotionLayoutExample2.setOnClickListener {
            startPageByScheme("motion-layout-example-2")
        }
        // 跳转到StateFlow示例
        binding.btnFlowStateFlowExample.setOnClickListener {
            startPageByScheme("state-flow-example")
        }
        // 跳转到StateFlow示例
        binding.btnFlowSharedFlowExample.setOnClickListener {
            startPageByScheme("shared-flow-example")
        }
        // 跳转到MotionLayout实践
        binding.btnMotionLayoutPractice.setOnClickListener {
            startPageByScheme("motion-layout-practice")
        }
        // 跳转到MotionLayout实践2
        binding.btnMotionLayoutPractice2.setOnClickListener {
            startPageByScheme("motion-layout-practice-2")
        }
    }
}
