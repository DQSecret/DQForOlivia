@file:Suppress("NoWildcardImports", "WildcardImport")

package com.example.dqddu.coroutines.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * Kotlin 的 Flow 学习
 *
 * @author DQDana For Olivia
 * @since 12/30/20 11:57 AM
 * @see <a href="https://juejin.cn/post/6844904057530908679">Kotlin Coroutines Flow 系列(一) Flow 基本使用</a>
 */
class FlowStudy {

    companion object {
        val simpleList = listOf(1, 2, 3, 4, 5)
        val simpleIntRange = 1..5
        const val defaultDuration = 300L
        const val randomIntMax = 100
        val randomInt
            get() = Random.Default.nextInt(1, randomIntMax)
        const val checkLimit = 3
    }

    /**
     * 创建Flow
     */
    suspend fun onTest1() {
        flow {
            for (i in simpleIntRange) {
                delay(defaultDuration)
                emit(i)
            }
        }.collect {
            it.logNow()
        }
        // 等同于一下两种写法
        flowOf(simpleList).onEach { delay(defaultDuration) }.collect { println(it) }
        listOf(simpleList).asFlow().onEach { delay(defaultDuration) }.collect { println(it) }
    }

    /**
     * channelFlow()
     * - 默认的 Flow 是冷流, 生产和消费是同步的
     * - 而 ChannelFlow 是热流, 生产和消费是异步的
     */
    @ExperimentalCoroutinesApi
    suspend fun onTest2() {

        measureTimeMillis {
            flow {
                for (i in simpleIntRange) {
                    delay(defaultDuration)
                    emit(i)
                }
            }.collect {
                delay(defaultDuration)
                println(it)
            }
        }.also {
            println("普通Flow花费时间: $it")
        }

        measureTimeMillis {
            flow {
                for (i in simpleIntRange) {
                    delay(defaultDuration)
                    emit(i)
                }
            }.flowOn(Dispatchers.IO).collect {
                delay(defaultDuration)
                println(it)
            }
        }.also {
            println("普通Flow异步collect花费时间: $it")
        }

        measureTimeMillis {
            channelFlow {
                for (i in simpleIntRange) {
                    delay(defaultDuration)
                    send(i)
                }
            }.collect {
                delay(defaultDuration)
                println(it)
            }
        }.also {
            println("ChannelFlow花费时间: $it")
        }
    }

    /**
     * 切换线程
     * - 不知道为啥, 1的第一次值, 就是比2的第一次值, 更快... 沮丧...
     */
    fun onTest3() {
        runBlocking {
            // 1
            (simpleIntRange).asFlow()
                .onEach { delay(defaultDuration) }
                .map { it * it }
                .flowOn(Dispatchers.IO)
                .collect { it.logNow(true) }
            // 2
            flow {
                for (i in simpleIntRange) {
                    delay(defaultDuration)
                    emit(i)
                }
            }
                .map { it * it }
                .flowOn(Dispatchers.IO)
                .collect { it.logNow(true) }
        }
    }

    /**
     * 一些常见的操作符
     * - collect
     * - single/first
     * - toList/toSet/toCollection
     * - count
     * - fold/reduce
     * - launchIn/produceIn/broadcastIn
     */
    fun onTest4() {
        runBlocking {
            (simpleIntRange).asFlow()
                .count { it > 0 }
                .also {
                    "($simpleIntRange) count=$it".log()
                }
        }
    }

    /**
     * 异常处理:
     * - catch: 只能捕获上流异常
     *   - 无法捕获下流异常
     * - onCompletion: 只能检测异常是否发生, 但是并不能处理异常
     *   - 如果出现在 catch 之后, 就无法检测异常了
     * - 收集端的异常
     *   - 可以使用 onEach 来前置业务逻辑
     *   - 此时 collect{} 将会无效化
     */
    fun onTest5() = runBlocking {
        // 发生在[发射端]的异常
        flow {
            for (i in simpleIntRange) {
                "Emitting $i".log()
                delay(defaultDuration)
                check(i < checkLimit) { "Crashed on $i" }
                emit(i)
            }
        }
            .catch { cause: Throwable ->
                cause.log()
            }
            .onCompletion { cause: Throwable? ->
                cause?.log()
            }
            .collect {
                "Collected $it".log()
            }
        // 发生在[收集端]的异常
        (simpleIntRange).asFlow()
            .onEach {
                // 模拟发射
                "Emitting $it".log()
                delay(defaultDuration)
            }
            .onEach {
                // 模拟收集
                check(it < checkLimit) { "Crashed on $it" }
                "onEach $it".log()
            }
            .onCompletion { cause: Throwable? ->
                "onCompletion before catch $cause".log()
            }
            .catch { cause: Throwable ->
                cause.log()
            }
            .onCompletion { cause: Throwable? ->
                "onCompletion After catch $cause".log()
            }
            /*.collect {
                "Collected $it".log()
            }*/ // 无效了
            .collect()
        // 异常出现时, 重试
        (simpleIntRange).asFlow()
            .onEach {
                check(it < randomIntMax / 2) { "Crashed on $it" }
                "Emitting $it".log()
            }
            .retryWhen { cause, attempt ->
                attempt < 2 && (cause is IllegalStateException).also {
                    "满足重试条件, 开始重试. 第${attempt}次".log()
                }
            }
            // .retry(2) { it is IllegalStateException } // 与 retryWhen 等价
            .catch { it.log() }
            .collect()
    }

    /**
     * Flow 的常见使用方式
     */
    fun onTest6() = runBlocking {
        // 假设网络请求,返回值为Int
        flow {
            delay(defaultDuration)
            // 随机返回一个 Int 值, 模拟网络返回
            emit(randomInt)
        }.onEach {
            "onTest6().onEach(): 发起了api请求,并返回了$it".log(true)
        }.flowOn(Dispatchers.IO)
            // 以上的代码会运行在 io 线程中
            // 以下的代码会运行在父作用域中 [CoroutineScope]
            .onStart {
                // 开始时, 展示 loading 等
                "onTest6().onStart()".logNow(true)
            }.onEach {
                // 收到数据后, 处理业务逻辑
                check(it > randomIntMax) { "Crashed on $it" }
                withContext(this.coroutineContext) {
                    "onTest6().onEach($it): 在这里处理业务".logNow(true)
                }
            }.onCompletion { cause: Throwable? ->
                // 结束后, 判断是正常结束or异常结束
                "onTest6().onCompletion() $cause".logNow(true)
            }.catch { cause: Throwable ->
                // 捕获异常, 不Crash, 可以上报日志啥的
                "onTest6().catch() $cause".logNow(true)
            }.collect()
    }
}

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    FlowStudy().run {
        // onTest1()
        // onTest2()
        // onTest3()
        // onTest4()
        // onTest5()
        onTest6()
    }
}
