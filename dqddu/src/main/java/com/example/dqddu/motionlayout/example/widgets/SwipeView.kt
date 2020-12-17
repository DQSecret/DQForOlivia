package com.example.dqddu.motionlayout.example.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.dqddu.R

/**
 * 左右滑出菜单栏的View
 *
 * @author DQDana For Olivia
 * @since 12/17/20 4:09 PM
 * @see <a href="文章">https://proandroiddev.com/building-swipeview-using-motionlayout-7a80fd06401c</a>
 */
class SwipeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_swipe_motion_layout, this)
    }
}