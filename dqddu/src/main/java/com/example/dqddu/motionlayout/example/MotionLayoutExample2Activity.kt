package com.example.dqddu.motionlayout.example

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityMotionLayoutExample2Binding
import com.google.android.material.appbar.AppBarLayout

/**
 * MotionLayout示例代码
 * 联系官方的 CodeLab
 *
 * @author DQDana For Olivia
 * @since 12/24/20 5:12 PM
 * @see <a href="url">https://codelabs.developers.google.com/codelabs/motion-layout#0</a>
 */
class MotionLayoutExample2Activity : BaseBindingActivity<ActivityMotionLayoutExample2Binding>() {

    override fun initBinding() = ActivityMotionLayoutExample2Binding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.motionStep5.tvPoetry.text = """
                           侠客行
                                     唐·李白
            赵客缦胡缨，吴钩霜雪明。
            银鞍照白马，飒沓如流星。
            十步杀一人，千里不留行。
            事了拂衣去，深藏身与名。
            闲过信陵饮，脱剑膝前横。
            将炙啖朱亥，持觞劝侯嬴。
            三杯吐然诺，五岳倒为轻。
            眼花耳热后，意气素霓生。
            救赵挥金槌，邯郸先震惊。
            千秋二壮士，烜赫大梁城。
            纵死侠骨香，不惭世上英。
            谁能书阁下，白首太玄经。
        """.trimIndent()
        coordinateMotion()
    }

    private fun coordinateMotion() {
        AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition =
                -verticalOffset / binding.motionStep5.appbarLayout.totalScrollRange.toFloat()
            binding.motionStep5.motionLayout.progress = seekPosition
        }.also {
            binding.motionStep5.appbarLayout.addOnOffsetChangedListener(it)
        }
    }
}
