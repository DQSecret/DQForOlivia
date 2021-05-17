package com.example.dqddu.list.paging3.view

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dqddu.R
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityPaging3StudyBinding

/**
 * 使用 Page3 实现分页列表
 *
 * @author DQDana For Olivia
 * @since 4/13/21 11:53 AM
 * @see <a href="https://medium.com/swlh/paging3-recyclerview-pagination-made-easy-333c7dfa8797">文章</a>
 */
class Paging3StudyActivity : BaseBindingActivity<ActivityPaging3StudyBinding>() {

    override fun initBinding() = ActivityPaging3StudyBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.viewBottomNavigation.setupWithNavController(
            findNavController(R.id.fragment_nav_host)
        )
    }
}
