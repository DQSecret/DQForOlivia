package com.example.dqddu.base

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * BaseApp
 *
 * @author DQDana For Olivia
 * @since 2020/11/26 4:24 PM
 */
class BaseApp : Application() {

    companion object {
        lateinit var app: BaseApp
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        app = this
    }

    override fun onCreate() {
        super.onCreate()
        // 初始化日志库
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}