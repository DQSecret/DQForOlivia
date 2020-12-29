package com.example.dqddu.coroutines.flow.shared

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivitySharedFlowExampleBinding

/**
 * SharedFlow 的实际使用案例
 *
 * @author DQDana For Olivia
 * @since 1/26/21 2:40 PM
 * @see <a href="https://juejin.cn/post/6918644407184916488">Kotlin StateFlow 实践案例</a>
 */
class SharedFlowExampleActivity : BaseBindingActivity<ActivitySharedFlowExampleBinding>() {

    override fun initBinding() = ActivitySharedFlowExampleBinding.inflate(layoutInflater)

    private val mViewModel: SharedFlowExampleActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObs()
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initObs() {
        mViewModel.logs.observe(this) { updateLogs(it) }
        mViewModel.events.asLiveData().observe(this) { updateLogs(it) }
    }

    private fun initViews() {
        mapOf<Button, CharSequence>(
            binding.btnUserA to binding.btnUserA.text,
            binding.btnUserB to binding.btnUserB.text,
            binding.btnUserC to binding.btnUserC.text,
        ).forEach { entry ->
            entry.key.setOnClickListener {
                // mViewModel.addLog(entry.value.toString())
                mViewModel.sendEvent(entry.value.toString())
            }
        }
        binding.btnClear.setOnClickListener {
            binding.tvLogs.text = ""
        }
    }

    private fun updateLogs(newLog: String) =
        binding.tvLogs.run {
            append(newLog + System.lineSeparator())
            post { binding.scrollContainer.fullScroll(ScrollView.FOCUS_DOWN) }
        }
}
