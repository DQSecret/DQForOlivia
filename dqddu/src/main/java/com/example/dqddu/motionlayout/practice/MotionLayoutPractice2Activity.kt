package com.example.dqddu.motionlayout.practice

import android.os.Bundle
import android.util.Log
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityMotionLayoutPractice2Binding
import com.google.android.material.appbar.AppBarLayout

/**
 * MotionLayout的实践2 - 话题头部
 *
 * @author DQDana For Olivia
 * @since 4/16/21 5:06 PM
 * @see <a href="无">无</a>
 */
class MotionLayoutPractice2Activity : BaseBindingActivity<ActivityMotionLayoutPractice2Binding>(),
    AppBarLayout.OnOffsetChangedListener {

    override fun initBinding() = ActivityMotionLayoutPractice2Binding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.layoutAppBar.addOnOffsetChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.layoutAppBar.removeOnOffsetChangedListener(this)
    }

    var tvIntroTop = -1
    var viewTitleSwitchHeight = -1

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        // 获取一下高度
        if (tvIntroTop < 0) {
            tvIntroTop = binding.tvIntro.top
        }
        if (viewTitleSwitchHeight < 0) {
            viewTitleSwitchHeight = binding.viewTitleSwitch.height
        }
        // 这里要计算高度,之后开始动画
        Log.d(
            "DQ",
            "onOffsetChanged: tvIntroTop={$tvIntroTop} | viewTitleSwitchHeight={$viewTitleSwitchHeight} | 已滑动距离={${-verticalOffset}} | 总距离={${appBarLayout?.totalScrollRange?.toFloat()}} "
        )
        val progress = (-verticalOffset) / (tvIntroTop - viewTitleSwitchHeight).toFloat()
        binding.viewTitleSwitch.setProgress(progress)
    }
}