package com.example.dqddu.motionlayout.practice.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dqddu.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 该类基本功能描述
 *
 * @author DQDana For Olivia
 * @since 4/16/21 6:10 PM
 * @see <a href="文章">外链描述</a>
 */
class TitleSwitchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var motion: MotionLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.view_title_switch, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        motion = findViewById(R.id.motion)

        setOnClickListener {
            MainScope().launch {
                for (i in 1..100) {
                    delay(100)
                    motion.progress = i.toFloat() / 100
                    println("DQ + ${i.toFloat() / 100}")
                }
            }
        }
    }

    /**
     * 让外部来触发
     */
    fun toEnd() {
        motion.transitionToEnd()
    }

    /**
     * 让外部来触发
     */
    fun toStart() {
        motion.transitionToStart()
    }
}