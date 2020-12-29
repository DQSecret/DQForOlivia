package com.example.dqddu.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dqddu.databinding.ActivityWebBinding
import com.example.dqddu.ext.toast
import com.just.agentweb.AgentWeb


/**
 * 利用 AgentWeb 实现的最简易 WebView 页面
 *
 * @author DQDana For Olivia
 * @since 2021/6/2 12:26 下午
 * @see <a href="https://github.com/Justson/AgentWeb">文章</a>
 */
class WebActivity : BaseBindingActivity<ActivityWebBinding>() {

    override fun initBinding() = ActivityWebBinding.inflate(layoutInflater)

    companion object {

        private const val URL = "url"

        fun start(context: Context, url: String) {
            Intent(context, WebActivity::class.java).apply {
                putExtra(URL, url)
            }.let {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(URL)?.let {
            initViews(it)
        } ?: toast("无效 url")
    }

    private fun initViews(url: String) {
        AgentWeb.with(this)
            .setAgentWebParent(
                binding.root,
                ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            )
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }
}