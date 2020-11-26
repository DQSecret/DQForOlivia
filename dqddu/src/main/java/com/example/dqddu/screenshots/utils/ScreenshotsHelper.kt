package com.example.dqddu.screenshots.utils

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

/**
 * 监听屏幕截屏的事件
 *
 * @author DQDana For Olivia
 * @see {https://proandroiddev.com/detect-screenshots-in-android-7bc4343ddce1}
 * @since 2020/11/25 11:43 AM
 */
class ScreenshotsHelper(private val context: Context) {

    private var contentObserver: ContentObserver? = null

    // 仅供测试测试
    val screenshotObs = MutableLiveData<String>()

    /**
     * 扩展方式, 方便创建
     */
    private fun ContentResolver.registerObserver(): ContentObserver {
        val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                uri?.let { queryScreenshots(it) }
            }
        }
        registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver)
        return contentObserver
    }

    fun start() {
        if (contentObserver == null) {
            contentObserver = context.contentResolver.registerObserver()
        }
    }

    fun stop() {
        contentObserver?.let { context.contentResolver.unregisterContentObserver(it) }
        contentObserver = null
    }

    /**
     * 屏幕截图的具体处理
     */
    private fun queryScreenshots(uri: Uri) {
        runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                queryRelativeDataColumn(uri)
            } else {
                queryDataColumn(uri)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    /**
     * 高版本使用
     */
    private fun queryRelativeDataColumn(uri: Uri) {
        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.RELATIVE_PATH,
        )
        context.contentResolver.query(
            uri, projection, null, null, null
        )?.use { cursor ->
            val relativePathColumn =
                cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)
            val displayNameColumn =
                cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val name = cursor.getString(displayNameColumn)
                val relativePath = cursor.getString(relativePathColumn)
                if (name.contains("screenshot", true)
                    || relativePath.contains("screenshot", true)
                ) {
                    // do something
                    Toast.makeText(context, name, Toast.LENGTH_LONG).show()
                    screenshotObs.postValue(name)
                }
            }
        }
    }

    /**
     * 低版本使用
     */
    private fun queryDataColumn(uri: Uri) {
        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
        )
        context.contentResolver.query(
            uri, projection, null, null, null
        )?.use { cursor ->
            val dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val path = cursor.getString(dataColumn)
                if (path.contains("screenshot", true)) {
                    // do something
                    Toast.makeText(context, path, Toast.LENGTH_LONG).show()
                    screenshotObs.postValue(path)
                }
            }
        }
    }
}