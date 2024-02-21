package com.example.dqddu.media

import android.os.Bundle
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityMediaDevCenterBinding

class MediaDevCenterActivity : BaseBindingActivity<ActivityMediaDevCenterBinding>() {
    override fun initBinding() = ActivityMediaDevCenterBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}