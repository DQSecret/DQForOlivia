package com.example.dqddu.coroutines.flow.state

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.coroutines.flow.logNow
import com.example.dqddu.databinding.ActivityStateFlowExampleBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect

/**
 * StateFlow 的实际使用案例
 *
 * @author DQDana For Olivia
 * @since 1/13/21 10:55 AM
 * @see <a href="https://juejin.cn/post/6876990111113248775#heading-12">Kotlin StateFlow 搜索功能的实践 DB + NetWork</a>
 */
@FlowPreview
@ExperimentalCoroutinesApi
class StateFlowExampleActivity : BaseBindingActivity<ActivityStateFlowExampleBinding>() {

    override fun initBinding() = ActivityStateFlowExampleBinding.inflate(layoutInflater)

    private val mViewModel: StateFlowExampleActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // mViewModel.queryKeywordsFromNetwork(it)
                    mViewModel.queryKeywordsByFlow(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // mViewModel.queryKeywordsFromNetwork(it)
                    mViewModel.queryKeywordsByFlow(it)
                }
                return true
            }
        })

        mViewModel.queryResultFromNetwork.observe(this, Observer { binding.tvResult.text = it })

        lifecycleScope.launchWhenStarted {
            mViewModel.result.collect {
                it.toString().logNow(true)
                when (it) {
                    is StateFlowExampleActivityVM.QueryResult.Success -> {
                        binding.tvResult.text = it.result
                    }
                    is StateFlowExampleActivityVM.QueryResult.Error -> {
                        binding.tvResult.text = it.exception.message
                    }
                }
            }
        }
    }
}