package com.example.dqddu.list

import android.os.Bundle
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityConcatAdapterStudyBinding

/**
 * 使用 ConcatAdapter 来分治长列表
 *
 * @author DQDana For Olivia
 * @since 4/13/21 12:25 PM
 * @see <a href="https://medium.com/@lucasnrb/divide-and-conquer-with-concatadapter-d0bb001502f9">文章</a>
 */
class ConcatAdapterStudyActivity : BaseBindingActivity<ActivityConcatAdapterStudyBinding>() {

    override fun initBinding() = ActivityConcatAdapterStudyBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvContent.text = this::class.simpleName.toString()
    }
}
