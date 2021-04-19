package com.example.dqddu.motionlayout.practice.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dqddu.databinding.ViewTitleSwitchBarBinding

/**
 * 一个可以渐变背景色的 ToolBar
 *
 * @author DQDana For Olivia
 * @since 4/16/21 6:10 PM
 * @see <a href="无">无</a>
 */
class TitleSwitchBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        ViewTitleSwitchBarBinding.inflate(LayoutInflater.from(getContext()), this, true)

    /**
     * 输入进度, 改变透明度
     */
    fun setProgress(progress: Float) {
        // 自己的透明度变化
        binding.motion.progress = progress
        // 子view的title切换动画
        val show = progress > 1
        if (show) {
            binding.viewTitleSwitch.toEnd()
        } else {
            binding.viewTitleSwitch.toStart()
        }
    }
}
