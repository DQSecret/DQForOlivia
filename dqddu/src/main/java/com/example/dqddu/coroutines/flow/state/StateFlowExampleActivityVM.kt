@file:Suppress("NoWildcardImports", "WildcardImport")

package com.example.dqddu.coroutines.flow.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dqddu.base.BaseApp
import com.example.dqddu.coroutines.flow.logNow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class StateFlowExampleActivityVM(app: Application = BaseApp.app) : AndroidViewModel(app) {

    companion object {
        const val DEBOUNCE_DURATION = 500L
        const val DELAY_SHORT = 500L
        const val DELAY_LONG = 1000L
    }

    private val _queryResultFromNetwork = MutableLiveData("")
    val queryResultFromNetwork: LiveData<String> = _queryResultFromNetwork

    /**
     * 模拟网络操作
     */
    fun queryKeywordsFromNetwork(keyword: String) {
        viewModelScope.launch {
            delay(DELAY_SHORT)
            _queryResultFromNetwork.value = "关键字[$keyword]的搜索结果是: ${keyword.reversed()}"
        }
    }

    private val _send = MutableStateFlow("")
    private val _receive = MutableStateFlow(QueryResult.Success(""))
    val result: StateFlow<QueryResult> = _receive

    /**
     * 使用 StateFlow 替代
     */
    fun queryKeywordsByFlow(keyword: String) = send(keyword)

    private fun send(keyword: String) {
        "send(): $keyword".logNow(true)
        _send.value = keyword
    }

    init {
        receive()
    }

    private fun receive() {
        viewModelScope.launch {
            _send
                .debounce(DEBOUNCE_DURATION)
                .flatMapLatest { mock(it) }
                .catch { cause: Throwable -> emit("网络发生错误了... o(╯□╰)o : $cause") }
                .collect {
                    "receive(): $it".logNow(true)
                    _receive.value = QueryResult.Success(it)
                }
        }
    }

    private fun mock(keyword: String) = flow {
        "mock(): $keyword".logNow(true)
        if (keyword.isBlank()) {
            emit("")
        } else {
            check(keyword != "null") { "关键字不能为[null]" }
            delay(DELAY_LONG)
            emit("关键字[$keyword]的搜索结果是: ${keyword.reversed()}")
        }
    }.flowOn(Dispatchers.IO)

    sealed class QueryResult {
        data class Success(val result: String) : QueryResult()
        data class Error(val exception: Throwable) : QueryResult()
    }
}
