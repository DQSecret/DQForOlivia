@file:Suppress("MagicNumber")

package com.example.dqddu.list.concat

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityConcatAdapterStudyBinding
import com.example.dqddu.ext.dp
import com.example.dqddu.ext.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private val concatAdapter by lazy { ConcatAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setupAdapters()
        setupListener()
        insertMockData()
    }

    private fun setupAdapters() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.addItemDecoration(DividerItemDecoration(this, VERTICAL))
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

    // basicInfoAdapter 的高度
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

    /**
     * 添加模拟数据
     */
    private fun insertMockData() {
        lifecycleScope.launch {
            delay(2000)
            concatAdapter.adapters
                .find {
                    it is MovieActorsAdapter
                }?.let {
                    it as MovieActorsAdapter
                }?.let {
                    val mockList = List(3) { Movie.Actor.getSimple() }
                    val mergeList = it.currentList.toMutableList().apply {
                        addAll(0, mockList)
                    }
                    it.submitList(mergeList)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.recycler.clearOnScrollListeners()
    }
}
