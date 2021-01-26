package com.example.dqddu.coroutines.flow.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dqddu.base.BaseApp
import com.example.dqddu.coroutines.flow.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.random.Random

/**
 * 详见[SharedFlowExampleActivity]
 *
 * @author DQDana For Olivia
 * @since 1/26/21 3:26 PM
 */
class SharedFlowExampleActivityVM(app: Application = BaseApp.app) : AndroidViewModel(app) {

    companion object {

        private const val RANDOM_INT_MAX = 1006
        private val cycles = 1..100
        private const val intervals = 1000L

        val randomInt
            get() = Random.Default.nextInt(1, RANDOM_INT_MAX)
    }

    // region 使用 LiveData 实现
    private val _logs: MutableLiveData<String> = MutableLiveData()
    val logs: LiveData<String> = _logs

    fun addLog(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in cycles) {
                "$user-随机数(No.$i)为: $randomInt".also {
                    _logs.postValue(it)
                    it.log(true)
                }
                delay(intervals)
            }
        }
    }
    // endregion

    // region 使用 SharedFlow 实现

    private val _event = MutableSharedFlow<String>()
    val events = _event.asSharedFlow()

    private suspend fun postEvent(event: String) {
        _event.emit(event)
        event.log(true)
    }

    private val mUserEventsMaps: MutableMap<String, Job> = mutableMapOf()

    /**
     * 从map中获取对应job, 如果有就cancel, 没有就创建
     */
    fun sendEvent(user: String) {
        mUserEventsMaps[user]
            ?.let {
                it.cancel()
                mUserEventsMaps.remove(user)
            }
            ?: mUserEventsMaps.put(user, createLoopJob(user))
    }

    private fun createLoopJob(user: String) =
        viewModelScope.launch(Dispatchers.IO) {
            postEvent("$user-开始发送")
            try {
                var i = 0
                while (isActive) {
                    delay(intervals)
                    i += 1
                    postEvent("$user-发送第(No.$i)次事件")
                }
            } catch (e: CancellationException) {
                postEvent("$user-停止发送(cancel)")
            } finally {
                postEvent("$user-结束发送")
            }
        }
    // endregion
}
