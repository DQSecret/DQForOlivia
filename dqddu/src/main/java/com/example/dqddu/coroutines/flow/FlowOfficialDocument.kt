@file:Suppress("NoWildcardImports", "WildcardImport")

package com.example.dqddu.coroutines.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/**
 * 官方文档的学习
 *
 * @author DQDana For Olivia
 * @since 12/30/20 2:24 PM
 * @see <a href="https://www.kotlincn.net/docs/reference/coroutines/flow.html">官方文档</a>
 */
@Suppress("MemberVisibilityCanBePrivate", "unused", "TooManyFunctions")
class FlowOfficialDocument {

    companion object {
        val simpleList = listOf(1, 2, 3, 4, 5)
        val simpleIntRange = 1..5
        val simpleStringList = listOf("DQ", "Dana", "Vera")
        const val defaultDuration = 300L
        const val defaultDurationLong = 1000L
        const val checkLimit = 3
    }

    /**
     * 挂起函数可以异步的返回单个值,但是该如何异步返回多个计算好的值呢?
     *
     * 这正是 Kotlin 流（Flow）的用武之地.
     */
    fun onTest1() {
        // List - 表示多个值
        simpleList.forEach { it.logNow() }
        // Sequence(序列)
        // 如果使用一些消耗 CPU 资源的阻塞代码计算数字（每次计算需要 defaultDuration 毫秒）
        // 那么我们可以使用 Sequence 来表示数字.
        sequence {
            for (i in simpleIntRange) {
                Thread.sleep(defaultDuration)
                yield(i)
            }
        }.forEach { it.logNow() }
        // 然而👆计算过程阻塞运行该代码的主线程。
        // 当这些值由异步代码计算时，我们可以使用 suspend 修饰符标记函数 simple
        // 这样它就可以在不阻塞的情况下执行其工作并将结果作为列表返回.
        "simple start".logNow()
        suspend fun simple(): List<Int> {
            delay(defaultDuration) // 假装我们在这里做了一些异步的事情
            return simpleList
        }
        "simple doing...".logNow()
        runBlocking {
            simple().forEach { it.logNow() }
        }
        "simple end".logNow()
    }

    /**
     * 经历过👆的[onTest1]可以发现 list & sequence & suspend 各有各的优势,
     *
     * 当我们想要, 异步的, 一个一个获取结果, 这就是 Flow(流) 的使用场景了.
     */
    @InternalCoroutinesApi
    fun onTest2() {
        fun simple(): Flow<Int> = flow {
            for (i in simpleIntRange) {
                delay(defaultDuration) // 假装我们在这里做了一些有用的事情
                emit(i) // 发送下一个值
            }
        }
        runBlocking {
            // 启动并发的协程以验证主线程并未阻塞
            launch {
                for (k in simpleIntRange) {
                    "I'm not blocked $k".logNow(true)
                    // Thread.sleep(defaultDuration) // 这样的话是会阻塞主线程的
                    delay(defaultDuration)
                }
            }
            // 收集这个流
            simple().collect { it.logNow(true) }
        }
    }

    /**
     * Flow 是一种类似于序列的冷流 — 这段 flow 构建器中的代码直到流被收集的时候才运行。
     *
     * ps:
     * 1. 冷/热流的区别, 冷(当被订阅时,才发数据), 热(创建时,就发数据,不管是否被订阅).
     * 2. eg: 冷->看视频,每次都从头看; 热->看直播,播到哪看哪,无法看之前的数据.
     *
     * 这在以下的示例中非常明显:
     */
    fun onTest3() {
        /**
         * 该方法不需要挂起(suspend)的原因是:
         * 因为 Flow 是冷的, 当没有被收集(collect)时, 不会触发执行耗时操作.
         * 所以会尽快返回且不进行任何等待.
         */
        fun simple(): Flow<Int> = flow {
            println("Flow started")
            for (i in simpleIntRange) {
                delay(defaultDuration)
                emit(i)
            }
        }
        runBlocking {
            println("Calling simple function...")
            val flow = simple()
            println("Calling collect...")
            flow.collect { it.logNow() }
            println("Calling collect again...")
            flow.collect { it.logNow() }
        }
    }

    /**
     * 1. 流是可取消的, 同挂起函数, 此处不做详解, 略过.
     * 2. 常用的一些过度流操作符:
     * map/filter 以及升级版 transform, 还是就是限长, 比如 take
     * 3. 末端流操作符
     * collect | toList&toSet | first&single | reduce&fold(规约)
     * 4. 流是连续的, 类似序列(sequence)
     * 5. 可以指定上下文
     */
    suspend fun onTest4() {
        // 规约
        simpleIntRange.asFlow()
            .map {
                it * it // 数字 1 至 5 的平方
            }.fold(1) { accumulator, value ->
                accumulator + value // 求和（末端操作符）
            }.let {
                println(it)
            }
        // 流是连续的
        simpleIntRange.asFlow()
            .filter {
                println("Filter $it")
                it % 2 == 0
            }
            .map {
                println("Map $it")
                "string $it"
            }.collect {
                println("Collect $it")
            }
        // 运行在指定上下文中, 在哪里collect(收集), 就在那里弄数据
        val simple = flowOf(simpleList)
        simple.collect { it.logNow(true) }
        withContext(Dispatchers.IO) {
            simple.collect { it.logNow(true) }
        }
    }

    /**
     * 更改流发射的上下文: 有缓冲的效果
     */
    fun onTest5() {
        fun simple() = flow {
            for (i in simpleIntRange) {
                delay(defaultDuration) // 假装我们以消耗 CPU 的方式进行计算
                "onTest5() Emitting $i".logNow(true)
                emit(i) // 发射下一个值
            }
        }.flowOn(Dispatchers.IO) // 在流构建器中改变消耗 CPU 代码上下文的正确方式
        runBlocking {
            val time = measureTimeMillis {
                simple().collect {
                    delay(defaultDuration)
                    "onTest5() Collected $it".logNow(true)
                }
            }
            println("Collected in $time ms")
        }
    }

    /**
     * 缓冲:
     *
     * 我们可以在流上使用 buffer 操作符来并发运行这个 simple 流中发射元素的代码以及收集的代码， 而不是顺序运行它们
     *
     * PS: flowOn 也有缓冲的功能
     */
    fun onTest6() {
        fun simple(): Flow<Int> = flow {
            for (i in simpleIntRange) {
                delay(defaultDuration) // 假装我们异步等待了 defaultDuration 毫秒
                emit(i) // 发射下一个值
            }
        }
        runBlocking {
            val time = measureTimeMillis {
                simple()
                    .buffer() // 缓冲发射项，无需等待
                    .collect { value ->
                        delay(defaultDuration) // 假装我们花费 defaultDuration 毫秒来处理它
                        println(value)
                    }
            }
            println("Collected in $time ms")
        }
    }

    /**
     * 合并(conflate), 跳过中间值, 所以发射代码必定是并发的
     * 处理最新值(collectLatest), 取消之前的值, 只收集最新的值
     */
    fun onTest7() {
        val simple = flow {
            for (i in simpleIntRange) {
                delay(defaultDuration)
                "onTest7() Emitting $i".logNow(true)
                emit(i)
            }
        }
        runBlocking {
            measureTimeMillis {
                simple.conflate().collect {
                    delay(defaultDuration) // 假装我们花费 defaultDuration 毫秒来处理它
                    "onTest7() conflate() Collected $it".logNow(true)
                }
                simple.collectLatest {
                    delay(defaultDuration) // 假装我们花费 defaultDuration 毫秒来处理它
                    "onTest7() Collected-Latest $it".logNow(true)
                }
            }.also {
                println("Collected in ${it}ms")
            }
        }
    }

    /**
     * 组合多个流
     * 1. Zip
     * 2. Combine
     */
    fun onTest8() = runBlocking {
        // zip 一一配对, 多余的去掉
        val numbers1 = (simpleIntRange).asFlow() // 数字 simpleIntRange
        val strings1 = flowOf("one", "two", "three", "four") // 字符串
        numbers1.zip(strings1) { a, b -> "$a -> $b" } // 组合单个字符串
            .collect { println(it) } // 收集并打印
        // zip 一一配对, 多余的去掉, 以耗时长的为准
        val numbers2 = (simpleIntRange).asFlow()
            .onEach { delay(defaultDuration) } // 发射数字 simpleIntRange，间隔 defaultDuration 毫秒
        val strings2 = flowOf("one", "two", "three", "four")
            .onEach { delay(defaultDuration) } // 每 defaultDuration 毫秒发射一次字符串
        val startTime2 = System.currentTimeMillis() // 记录开始的时间
        numbers2.zip(strings2) { a, b -> "$a -> $b" } // 使用“zip”组合单个字符串
            .collect { value -> // 收集并打印
                println("$value at ${System.currentTimeMillis() - startTime2} ms from start")
            }
        // combine 只要发射端有更新,收集端就执行
        val numbers3 = (simpleIntRange).asFlow()
            .onEach { delay(defaultDuration) } // 发射数字 simpleIntRange，间隔 defaultDuration 毫秒
        val strings3 = flowOf("one", "two", "three", "four")
            .onEach { delay(defaultDuration) } // 每 defaultDuration 毫秒发射一次字符串
        val startTime3 = System.currentTimeMillis() // 记录开始的时间
        numbers3.combine(strings3) { a, b -> "$a -> $b" } // 使用“combine”组合单个字符串
            .collect { value -> // 收集并打印
                println("$value at ${System.currentTimeMillis() - startTime3} ms from start")
            }
    }

    /**
     * 展平流
     * 1. flatMapConcat - 不漏掉任何一个值, 顺序等待(时间长)
     * 2. flatMapMerge - 不漏掉任何一个值, 在发射端按并发处理(时间短)
     * 3. flatMapLatest - 在发出新流后, 立即取消先前流的收集, 会忽略中间值(时间最短)
     */
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun onTest9() = runBlocking {
        // ... 这里看官方示例吧, 懒得写了=·=~
        // 第二次回头看, 就不懂了, 所以还是记录下来 =^=!

        // 用这么模拟网络请求, eg: 输入框输入一个关键字后, 获取搜索结果
        fun requestFlow(keyword: String): Flow<String> = flow {
            delay(defaultDurationLong)
            emit("$keyword -> ${keyword.reversed()}")
        }
        // 方便看日志
        val startTime = System.currentTimeMillis() // 记录开始时间
        fun getDurationLog() = "at ${System.currentTimeMillis() - startTime} ms from start"
        // 在这里模拟用户在输入框输入关键字
        simpleStringList.asFlow().onEach {
            delay(defaultDuration)
            "用户输入了: $it ${getDurationLog()}".log(true)
        }.flowOn(Dispatchers.IO) // 自带 buffer 效果
            // 想要看到[flatMapConcat]的效果, 需要把👆一行[.flowOn(Dispatchers.IO)]注释掉
            // .flatMapConcat { requestFlow(it) }
            // .flatMapMerge { requestFlow(it) }
            .flatMapLatest { requestFlow(it) }
            .collect {
                // val type = "flatMapConcat"
                // val type = "flatMapMerge"
                val type = "flatMapLatest"
                "onTest9().$type(): 请求结果为($it) ${getDurationLog()}".log(true)
            }
    }

    /**
     * 流异常
     * 1. try-catch
     *    - 包裹住 collect 会把发射端和收集端所有的异常全部捕获, 一旦发生异常, 会终止整个流
     *    - 无法封装
     * 2. .catch
     *    - 异常透明性(异常传递), 只能捕获上流的异常
     *    - 下流的异常(collect), 会逃逸
     * 3. 声明式捕获
     *    - 将 collect 中的逻辑, 放到 onEach 中实现
     */
    fun onTest10() {
        val simple = flow {
            for (i in simpleIntRange) {
                println("Emitting $i")
                emit(i) // 发射下一个值
            }
        }.map { value ->
            check(value <= 1) { "Crashed on $value" }
            "string $value"
        }
        // 全局包裹
        "全局包裹".logNow()
        runBlocking {
            kotlin.runCatching {
                simple.collect { value -> println(value) }
            }.onFailure {
                println("Caught $it")
            }
        }
        // 异常透明性
        "异常透明性".logNow()
        runBlocking {
            simple
                .catch { e -> emit("Caught $e") } // 发射一个异常
                .collect { value -> println(value) }
        }
        // 下流(collect)异常的逃逸
        "下流(collect)异常的逃逸".logNow()
        val simple2 = flow {
            for (i in simpleIntRange) {
                println("Emitting $i")
                emit(i)
            }
        }
        runBlocking {
            simple2
                .catch { e -> println("Caught $e") } // 不会捕获下游异常
                .collect { value ->
                    // 先注释掉, 不然影响👇的代码的执行
                    // check(value <= 1) { "Collected $value" }
                    println(value)
                }
        }
        // 声明式捕获
        "声明式捕获".logNow()
        runBlocking {
            simple2
                .onEach { value ->
                    check(value <= 1) { "Collected $value" }
                    println(value)
                }
                .catch { e -> println("Caught $e") }
                .collect()
        }
    }

    /**
     * 流完成时, 执行某些操作
     * 1. 命令式
     * 2. 声明式
     */
    @ExperimentalCoroutinesApi
    fun onTest11() {
        // 命令式
        fun simple(): Flow<Int> = (simpleIntRange).asFlow()
        runBlocking {
            try {
                simple().collect { value -> println(value) }
            } finally {
                println("Done")
            }
        }
        // 声明式1 - 会捕获上流异常
        /*fun simple2(): Flow<Int> = flow {
            emit(1)
            throw  RuntimeException("声明式 onCompletion 捕获异常")
        }
        runBlocking {
            simple2()
                .onCompletion { cause: Throwable? ->
                    if (cause != null) println("Flow completed exceptionally")
                    println("Done")
                }
                .catch { cause: Throwable ->
                    println("Caught exception: ${cause.printStackTrace()}")
                }
                .collect { value -> println(value) }
        }*/
        // 声明式2 - 也会捕获下流异常 PS: catch只能捕获上流异常
        runBlocking {
            simple()
                .onCompletion { cause -> println("Flow completed with $cause") }
                .collect { value ->
                    // 没有发生异常的情况下, [.onCompletion.cause]为null, 且没有终止
                    // check(value <= 1) { "Collected $value" }
                    println(value)
                }
        }
    }

    /**
     * 启动流
     * 1. 使用 onEach 来模拟
     * 2. 使用 launchIn 在单独的协程里收集,
     *    1. 初看起来, launchIn & flowOn 好像差不多
     *    2. launchIn 是收集端, 指数据的处理
     *    3. flowOn 是发射端, 指事件的发射
     * 3. launchIn 返回了一个 job, 可用于手动取消, 默认则是跟随 CoroutineScope 的生命周期
     */
    fun onTest12() {
        // 模仿事件流
        val simple = (simpleIntRange).asFlow().onEach { delay(defaultDuration) }
        runBlocking {
            val job = simple
                .onEach { it.logNow() }
                // .flowOn(Dispatchers.IO)
                // .collect()
                .launchIn(this)
            delay(defaultDuration)
            job.cancel()
            "Done".logNow()
        }
    }

    /**
     * 流取消检测
     * 1. 流默认是可以自己取消的
     */
    fun onTest13() {
        // 默认可取消, 因为 emit 处有挂起点
        /*val simple = flow {
            for (i in simpleIntRange) {
                "Emitting $i".logNow()
                emit(i)
            }
        }
        runBlocking {
            simple
                .collect {
                    if (it == 3) cancel()
                    "Collected $it".logNow()
                }
        }*/
        // 其他扩展函数,出于性能原因,不会自动执行取消检测 eg:
        runBlocking {
            simpleIntRange.asFlow()
                /*.onEach {
                    this.coroutineContext.ensureActive()
                }*/ // 等同于 .cancellable()
                .cancellable()
                .collect { value ->
                    if (value == checkLimit) cancel()
                    println(value)
                }
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun main() = runBlocking {
    FlowOfficialDocument().run {
        "FlowOfficialDocument().run(): start:".logNow(postfix = "\n")
        // onTest1()
        // onTest2()
        // onTest3()
        // onTest4()
        // onTest5()
        // onTest6()
        // onTest7()
        // onTest8()
        onTest9()
        // onTest10()
        // onTest11()
        // onTest12()
        // onTest13()
        "FlowOfficialDocument().run(): end.".logNow(prefix = "\n")
    }
}
