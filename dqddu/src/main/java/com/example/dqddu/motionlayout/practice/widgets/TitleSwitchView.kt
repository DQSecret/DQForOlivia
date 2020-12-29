package com.example.dqddu.motionlayout.practice.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dqddu.databinding.ViewTitleSwitchBinding

/**
 * 主标题和副标题(快速滚动) - 目前用于[TitleSwitchBarView]
 *
 * @author DQDana For Olivia
 * @since 4/19/21 2:55 PM
 * @see <a href="无">无</a>
 */
class TitleSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        ViewTitleSwitchBinding.inflate(LayoutInflater.from(getContext()), this, true)

    private var transitioning = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.motion.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                transitioning = false
            }
        })
    }

    /**
     * 让外部来触发
     */
    fun toEnd() {
        if (!transitioning && binding.motion.currentState != binding.motion.endState) {
            binding.motion.transitionToEnd()
            transitioning = true
        }
    }

    /**
     * 让外部来触发
     */
    fun toStart() {
        if (!transitioning && binding.motion.currentState != binding.motion.startState) {
            binding.motion.transitionToStart()
            transitioning = true
        }
    }
}
