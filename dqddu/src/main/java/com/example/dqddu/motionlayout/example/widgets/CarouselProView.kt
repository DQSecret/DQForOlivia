package com.example.dqddu.motionlayout.example.widgets

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.content.ContextCompat
import com.example.dqddu.R
import com.example.dqddu.databinding.ViewCarouselProMotionLayoutBinding
import com.example.dqddu.ext.dp


/**
 * 轮播组件
 * <p>
 * 目前看不到通用性,但比起[CarouselView]有所优化
 * <p/>
 *
 * @author DQ For Olivia
 * @since 2021/8/5 2:58 下午
 * @see <a href="https://www.youtube.com/watch?v=29gLA90m6Gk">视频中简单的描述了一两句,尝试实践</a>
 */
class CarouselProView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCarouselProMotionLayoutBinding

    private val colorsResId = listOf(R.color.red_500, R.color.teal_500, R.color.orange_500)
    private val drawables: List<GradientDrawable> = colorsResId.map {
        GradientDrawable().apply {
            setColor(ContextCompat.getColor(context, it))
            cornerRadius = 8.dp.toFloat()
        }
    }

    init {
        ViewCarouselProMotionLayoutBinding.inflate(LayoutInflater.from(getContext()), this, true)
            .also {
                binding = it
            }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 添加动画监听器
        binding.viewCarouselProRoot.setTransitionListener(object : TransitionAdapter() {
            private var index = 1
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                // TODO: 2021/8/6 这些代码实现轮播, 但是会闪一下,
                //  后续尝试使用 androidx.constraintlayout.helper.widget.Carousel 来实现
//                if (currentId == R.id.next) {
//                    index += 1
//                    if (index > drawables.lastIndex) {
//                        index = 0
//                    }
//                    updateData(index)
//                    binding.viewCarouselProRoot.progress = 0f
//                } else if (currentId == R.id.previous) {
//                    index -= 1
//                    if (index < 0) {
//                        index = drawables.lastIndex
//                    }
//                    updateData(index)
//                    binding.viewCarouselProRoot.progress = 0f
//                }
            }
        })
    }

    /**
     * 更换数据
     */
    private fun updateData(index: Int) {
        when (index) {
            0 -> {
                binding.left.setImageDrawable(drawables.last())
                binding.middle.setImageDrawable(drawables[index])
                binding.right.setImageDrawable(drawables[index + 1])
            }
            in 1 until drawables.lastIndex -> {
                binding.left.setImageDrawable(drawables[index - 1])
                binding.middle.setImageDrawable(drawables[index])
                binding.right.setImageDrawable(drawables[index + 1])
            }
            drawables.lastIndex -> {
                binding.left.setImageDrawable(drawables[index - 1])
                binding.middle.setImageDrawable(drawables[index])
                binding.right.setImageDrawable(drawables.first())
            }
        }
    }
}