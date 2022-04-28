package com.example.dqddu.views

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.annotation.IntRange
import androidx.appcompat.widget.AppCompatTextView

/**
 * 能够自定义缩略文本的TextView
 * 1. 指定最大行数
 * 2. 指定后缀
 *
 * @author DQ For Olivia
 * @since 2022/4/27 19:48
 */
class EllipsizeViewPlus @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        // 无宽字符:感谢鑫总的帮助
        private const val ZERO_WIDTH_SPACE = "\u200B"
        private const val ELLIPSIS_TEXT: String = "..."
    }

    fun setTextEllipsize(
        content: String = "扬名立万的DQForOlivia接受《环球时报》专访",
        @IntRange(from = 2) maxLines: Int = 2,
        delimiter: String = "》"
    ) {
        // 为了获得TextView的宽度
        post {
            // 获取可绘制的最大宽度
            val maxWidth = measuredWidth - paddingStart - paddingRight
            // 利用StaticLayout来测量多行文字
            val layout = createStaticLayout(content, maxWidth)
            // 如果小于最大行数,则直接绘制即可
            if (layout.lineCount <= maxLines) {
                text = layout.text
                return@post
            }
            // 确定后缀
            val index = content.lastIndexOf(delimiter)
            val tail = if (index < 0) {
                ELLIPSIS_TEXT
            } else {
                "$ELLIPSIS_TEXT${content.takeLast(content.length - index)}"
            }
            // 循环裁剪,直到小于等于最大行数
            var cropIndex = if (index < 0) content.length else index
            while (true) {
                val temp = createStaticLayout((content.take(cropIndex) + tail), maxWidth)
                if (temp.lineCount <= maxLines) {
                    text = temp.text
                    break
                } else {
                    cropIndex -= 1
                }
            }
        }
    }

    /**
     * 利用StaticLayout来测量多行文字
     */
    private fun createStaticLayout(content: CharSequence, maxWidth: Int): StaticLayout {
        val contextWithSpace =
            content.toMutableList().joinToString(ZERO_WIDTH_SPACE, postfix = ZERO_WIDTH_SPACE)
        return StaticLayout(
            contextWithSpace,
            paint,
            maxWidth,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            0f,
            true
        ).also {
            println("[${it.text}], 需要${it.lineCount}行才能绘制完整")
        }
    }
}

fun main() {
    // val view: EllipsizeViewPlus = EllipsizeViewPlus()
    // 正常
    // view.setTextEllipsize("扬名立万的DQForOlivia接受《环球时报》专访", 2, "》");
    // 没有"》"
    // view.setTextEllipsize("扬名立万的DQForOlivia接受《环球时报专访", 2, "》");
    // 多个"》"
    // view.setTextEllipsize("DQ接受了《XXXX》和《一人之下》剧组的采访", 2, "》");
    // 后缀超过一行
    // view.setTextEllipsize("DQ接受了《XXXX》和《一人之下》剧组的采访,哈哈哈哈.", 2, "》");
    // 更改最大行数
    // view.setTextEllipsize("DQ接受了《异人Olivia》和《一人之下》剧组的采访,哈哈哈哈.", 3, "》");
    // 最大行数,满足显示全部文字
    // view.setTextEllipsize("DQ接受了《XXXX》和《一人之下》剧组的采访,哈哈哈哈.", 4, "》");
}