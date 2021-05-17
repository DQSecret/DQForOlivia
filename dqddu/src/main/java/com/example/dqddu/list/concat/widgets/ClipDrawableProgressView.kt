package com.example.dqddu.list.concat.widgets

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.annotation.IntRange
import androidx.appcompat.widget.AppCompatImageView
import com.example.dqddu.R

/**
 * 渐变进度条
 *
 * @author DQDana For Olivia
 * @since 4/25/21 10:33 AM
 * @see <a href="https://bterczynski.medium.com/android-clipdrawable-897a308ca58">文章</a>
 */
class ClipDrawableProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        setBackgroundResource(R.drawable.view_clip_drawable_progress)
    }

    fun setProgress(progress: Float) {
        setValue((progress * 10000).toInt())
    }

    private fun setValue(@IntRange(from = 0, to = 10000) level: Int) {
        this.background.let {
            it as LayerDrawable
        }.let {
            it.findDrawableByLayerId(R.id.indicator) as ClipDrawable
        }.run {
            this.setLevel(level)
        }
    }
}
