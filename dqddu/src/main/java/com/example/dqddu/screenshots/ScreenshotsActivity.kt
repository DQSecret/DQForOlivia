package com.example.dqddu.screenshots

import android.os.Bundle
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityScreenshotsBinding
import com.example.dqddu.screenshots.utils.ScreenshotsHelper

class ScreenshotsActivity : BaseBindingActivity<ActivityScreenshotsBinding>() {

    override fun initBinding() = ActivityScreenshotsBinding.inflate(layoutInflater)

    private val mScreenshots by lazy { ScreenshotsHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScreenshots.screenshotObs.observe(this) {
            binding.tvScreenshots.text = it
        }
    }

    override fun onStart() {
        super.onStart()
        mScreenshots.start()
    }

    override fun onStop() {
        super.onStop()
        mScreenshots.stop()
    }
}