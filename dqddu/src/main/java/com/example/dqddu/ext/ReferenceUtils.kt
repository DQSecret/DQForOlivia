package com.example.dqddu.ext

import java.lang.ref.WeakReference

/**
 * Reference 相关扩展函数汇总
 *
 * @author DQDana For Olivia
 * @since 4/12/21 6:50 PM
 * @see <a href="https://betterprogramming.pub/22-kotlin-extensions-for-cleaner-code-acadcbd49357">文章</a>
 */

/**
 * 方便创建弱引用
 */
val <T> T.weak: WeakReference<T>
    get() = WeakReference(this)
