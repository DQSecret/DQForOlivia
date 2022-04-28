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
 * > 写到最后,发现越写越复杂,所以直接新起炉灶. [EllipsizeViewPlus]
 *
 * @author DQ For Olivia
 * @since 2022/4/27 19:48
 */
class EllipsizeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        // 无宽字符:感谢鑫总的帮助
        private const val ZERO_WIDTH_SPACE = "\u200B"
    }

    fun setTextEllipsize(
        content: String = "扬名立万的DQForOlivia接受《环球时报》专访",
        @IntRange(from = 2) maxLines: Int = 2,
        delimiter: String = "》"
    ) {
        // 为了获得TextView的宽度
        post {
            // 先判断是否超过最大行数
            val maxWidth = measuredWidth - paddingStart - paddingRight
            println("maxWidth = ${maxWidth}")
            val contextWithSpace = content.toMutableList().joinToString(ZERO_WIDTH_SPACE)
            // 利用StaticLayout来测量多行文字
            val layout = StaticLayout(
                contextWithSpace,
                paint,
                maxWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                0f,
                true
            )
            // 如果小于最大行数,则直接绘制即可
            println("layout.lineCount = ${layout.lineCount}")
            if (layout.lineCount <= maxLines) {
                text = contextWithSpace
                return@post
            }
            // 尾
            val index = contextWithSpace.lastIndexOf(delimiter).let {
                // 如果没有找到 delimiter 默认最后追加...
                if (it < 0) contextWithSpace.length else it
            }
            val tail = "...${contextWithSpace.takeLast(contextWithSpace.length - index)}"
            println("tail = ${tail}")
            var offsetLine: Int
            val tailWidth = getDesiredWidth(tail).let {
                println("tailWidth(总) = ${it}")
                // 如果 tailWidth 超过了 maxWidth 就往上提一行
                offsetLine = (it / maxWidth).toInt()
                // TODO: 2022/4/28 这里涉及到 tail 本身就换行的问题,更复杂了,简单的取模是不行的
                val remainder = getTailWidth(maxWidth, tail)
                // return@let it % maxWidth
                return@let remainder
            }
            println("offsetLine = ${offsetLine}")
            println("tailWidth(余) = ${tailWidth}")

            // 计算最后一行展示多少文字
            val secondLastIndex = layout.getLineEnd(maxLines - 2 - offsetLine) // 获取倒数第二行的最后一个字符的下标
            val lastIndex = layout.getLineEnd(maxLines - 1 - offsetLine) // 获取倒数第一行的最后一个字符的下标
            val lastLineConte = layout.text.substring(secondLastIndex, lastIndex)
            println("lastLineConte = ${lastLineConte}")


            // 持续缩减最后一行的文本,直到可以容纳尾
            var drop = 1
            while (true) {
                val curr = lastLineConte.dropLast(drop)
                val currW = getDesiredWidth(curr)
                val isEnough = (currW + tailWidth) < maxWidth
                if (!isEnough) {
                    drop += 2
                } else {
                    break
                }
            }
            // 最终结果
            val result: String = contextWithSpace.take(lastIndex - drop) + tail
            println("result = ${result}")
            text = result
        }
    }

    /**
     * 计算所需宽度
     */
    private fun getDesiredWidth(content: CharSequence): Float {
        return StaticLayout.getDesiredWidth(content, paint)
    }

    /**
     * 计算尾部需要的长度,由于涉及到尾部过长,需要换行,就更麻烦了
     */
    private fun getTailWidth(maxWidth: Int, tail: String): Float {
        val layout = StaticLayout(
            tail.reversed(), // 这里翻转一下,是为了计算正确的尾行的宽度
            paint,
            maxWidth,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            0f,
            true
        )
        println("尾:maxWidth = ${maxWidth}")
        println("尾:lineCount = ${layout.lineCount}")
        val secondLastIndex = layout.getLineEnd(layout.lineCount - 2)
        val lastIndex = layout.getLineEnd(layout.lineCount - 1)
        println("尾:lastIndex = ${lastIndex}, secondLastIndex = ${secondLastIndex}")
        val lastLineConte = layout.text.substring(secondLastIndex, lastIndex)
        println("尾:lastLineConte = ${lastLineConte}")
        return getDesiredWidth(lastLineConte).also {
            println("尾:lastLine的长度 = ${it}")
        }
    }
}