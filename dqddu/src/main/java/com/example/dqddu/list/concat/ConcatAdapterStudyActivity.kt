package com.example.dqddu.list.concat

import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityConcatAdapterStudyBinding
import com.example.dqddu.ext.dp
import com.example.dqddu.ext.toast
import kotlin.math.max

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
        initView()
    }

    private fun initView() {
        setupAdapters()
        setupListener()
    }

    private fun setupAdapters() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        val concatAdapter = ConcatAdapter()
        val basicInfoAdapter = MovieBasicInfoAdapter(Movie.simple) {
            toast("点击了 [MovieBasicInfoAdapter] 项.")
        }
        val introductionAdapter = MovieIntroductionAdapter(Movie.simple) {
            toast("点击了 [MovieIntroductionAdapter] 项.")
        }
        val actorsAdapter = MovieActorsAdapter(Movie.simple) {
            toast("点击了 [MovieActorsAdapter] 项.")
        }
        concatAdapter.addAdapter(basicInfoAdapter)
        concatAdapter.addAdapter(introductionAdapter)
        concatAdapter.addAdapter(actorsAdapter)
        binding.recycler.adapter = concatAdapter
    }

    private var mMaxScroll = 172.dp
    private var mScrollY = -1F

    private fun setupListener() {
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollY += dy.toFloat()
                mScrollY = max(mScrollY, 0F)
                val progress = mScrollY / mMaxScroll
                binding.viewProgress.setProgress(progress)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.recycler.clearOnScrollListeners()
    }
}
