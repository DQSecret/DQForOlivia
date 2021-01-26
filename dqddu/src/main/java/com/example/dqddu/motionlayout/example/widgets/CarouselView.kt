package com.example.dqddu.motionlayout.example.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.dqddu.R
import com.example.dqddu.base.BaseApp
import com.example.dqddu.databinding.ViewCarouselMotionLayoutBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 尝试实现纵向轮播的组件
 *
 * @author DQDana For Olivia
 * @since 12/17/20 4:21 PM
 */
class CarouselView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCarouselMotionLayoutBinding
    private val maps: Map<View, String>

    init {
        ViewCarouselMotionLayoutBinding.inflate(LayoutInflater.from(getContext()), this, true)
            .also {
                binding = it
                maps = mapOf(
                    binding.tvItemFirst to "First",
                    binding.tvItemSecond to "Second",
                    binding.tvItemThird to "Third",
                    binding.tvItemAnother to "First",
                )
            }
    }

    companion object {
        const val TRANSITION_PROGRESS_UPPER_LIMITS = 0.8f
        const val TRANSITION_PROGRESS_LOWER_LIMITS = 0.2f
        const val TRANSITION_RE_EVOKE_INTERVAL = 4_000L
    }

    fun init() {
        Log.d("DQ", "init: ")
        binding.tvItemFirst.apply {
            text = maps[this]
            setOnClickListener { onPause() }
        }
        binding.tvItemSecond.apply {
            text = maps[this]
            setOnClickListener { onPause() }
        }
        binding.tvItemThird.apply {
            text = maps[this]
            setOnClickListener { onPause() }
        }
        binding.tvItemAnother.apply {
            text = maps[this]
            setOnClickListener { onPause() }
        }
        binding.viewCarouselRoot.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                if (progress > TRANSITION_PROGRESS_UPPER_LIMITS) {
                    if (!isPaused) {
                        Log.d("DQ", "onTransitionChange: 到${progress}了, 重置然后循环...")
                        motionLayout?.progress = TRANSITION_PROGRESS_LOWER_LIMITS
                        motionLayout?.transitionToEnd()
                    }
                }
            }
        })
        binding.viewCarouselRoot.transitionToState(R.id.end)
    }

    private var isPaused = false
    private fun TextView.onPause() {
        // 1. 先暂停
        isPaused = true
        val progress = binding.viewCarouselRoot.progress
        binding.viewCarouselRoot.progress = progress
        // 2. 提示用户
        maps[this]?.toast()
        Log.d("DQ", "onPause: 在${progress}时被人为点击了, 暂停4s.")
        // 3. 延迟4s恢复
        context.takeIf {
            it is LifecycleOwner
        }?.let {
            it as LifecycleOwner
            it.lifecycleScope.launch {
                delay(TRANSITION_RE_EVOKE_INTERVAL)
                Log.d("DQ", "onPause: 4s到了, 恢复动画~")
                isPaused = false
                binding.viewCarouselRoot.progress = progress
                binding.viewCarouselRoot.transitionToState(R.id.end)
            }
        }
    }

    private fun CharSequence.toast() =
        Toast.makeText(BaseApp.app.applicationContext, this, Toast.LENGTH_SHORT).show()
}
