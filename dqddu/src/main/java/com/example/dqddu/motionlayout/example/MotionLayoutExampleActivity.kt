package com.example.dqddu.motionlayout.example

import androidx.lifecycle.lifecycleScope
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityMotionLayoutExampleBinding

/**
 * MotionLayout示例代码
 *
 * @author DQDana For Olivia
 * @since 2020/12/7 10:57 AM
 */
class MotionLayoutExampleActivity : BaseBindingActivity<ActivityMotionLayoutExampleBinding>() {

    override fun initBinding() = ActivityMotionLayoutExampleBinding.inflate(layoutInflater)

    init {
        lifecycleScope.launchWhenResumed { binding.viewCarousel.init() }
    }
}
