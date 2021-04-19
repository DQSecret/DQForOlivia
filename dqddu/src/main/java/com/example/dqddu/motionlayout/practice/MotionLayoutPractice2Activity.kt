package com.example.dqddu.motionlayout.practice

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import androidx.core.graphics.toColorInt
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityMotionLayoutPractice2Binding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.roundToInt

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
        binding.layoutRefresh.apply {
            setProgressViewOffset(true, 15.dp, 64.dp)
            setColorSchemeColors("#f34d41".toColorInt())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.layoutAppBar.removeOnOffsetChangedListener(this)
    }

    var tvIntroTop = -1
    var viewTitleSwitchHeight = -1

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        // 获取高度
        if (tvIntroTop < 0) {
            tvIntroTop = binding.tvIntro.top
        }
        if (viewTitleSwitchHeight < 0) {
            viewTitleSwitchHeight = binding.viewTitleSwitchBar.height
        }
        // 这里要计算高度,之后开始动画
        val progress = (-verticalOffset) / (tvIntroTop - viewTitleSwitchHeight).toFloat()
        binding.viewTitleSwitchBar.setProgress(progress)
        // 状态变化 -> 是否可以刷新
        if (verticalOffset == 0) {
            binding.layoutRefresh.isEnabled = true
        } else {
            binding.layoutRefresh.isRefreshing = false
            binding.layoutRefresh.isEnabled = false
        }
    }

    private val Number.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).roundToInt()
}