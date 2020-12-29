package com.example.dqddu.coroutines.example.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityNetworkExampleBinding

/**
 * 常用网站的列表页
 *
 * @author DQDana For Olivia
 * @since 2020/12/3 5:34 PM
 * @see <a href="文章">https://medium.com/canopas/android-reactive-programming-with-coroutines-and-mvvm-74cd4117df3a</a>
 */
class NetworkExampleActivity : BaseBindingActivity<ActivityNetworkExampleBinding>() {

    override fun initBinding() = ActivityNetworkExampleBinding.inflate(layoutInflater)

    private val vm by viewModels<NetworkExampleActivityVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObs()
        initView()
    }

    private fun initObs() {
        vm.mResultStatusObs.observe(this) {
            binding.tvResultStatus.text = it
        }
        vm.mResultContentObs.observe(this) {
            binding.tvResultContent.text = it.joinToString("\n")
        }
    }

    private fun initView() {
        binding.btnMockSuccess.setOnClickListener { vm.getCommonlyUsedWebSiteListRepoAsync(true) }
        binding.btnMockFailure.setOnClickListener { vm.getCommonlyUsedWebSiteListRepoAsync(false) }
    }
}
