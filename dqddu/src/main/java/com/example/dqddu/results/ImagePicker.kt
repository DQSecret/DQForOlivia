package com.example.dqddu.results

import android.Manifest
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.dqddu.BuildConfig
import com.example.dqddu.R
import com.example.dqddu.ext.toast
import java.io.File

class ImagePicker(
    private val activity: ComponentActivity,
    private val onImagePickerResult: (Uri) -> Unit
) : DefaultLifecycleObserver {

    init {
        activity.lifecycle.addObserver(this)
    }

    private lateinit var requestCameraPermission: ActivityResultLauncher<String>
    private lateinit var getImageContent: ActivityResultLauncher<String>
    private lateinit var takePicture: ActivityResultLauncher<Uri>

    private fun requestPermissionAndTakePicture() {
        requestCameraPermission.launch(Manifest.permission.CAMERA)
    }

    private fun selectImage() {
        getImageContent.launch("image/*")
    }

    private fun takePicture() {
        takePicture.launch(getCameraCacheUri())
    }

    private val uniqueKey = System.currentTimeMillis().toString()

    /**
     * 绑定生命周期
     */
    override fun onCreate(owner: LifecycleOwner) {
        requestCameraPermission = activity.activityResultRegistry.register(
            "requestCameraPermission_$uniqueKey",
            owner,
            ActivityResultContracts.RequestPermission(),
            { isGranted ->
                if (isGranted) {
                    takePicture()
                } else {
                    toast(msg = "Permission not granted.")
                }
            })

        getImageContent = activity.activityResultRegistry.register(
            "getImageContent_$uniqueKey",
            owner,
            ActivityResultContracts.GetContent(),
            { uri ->
                if (uri != null) {
                    onImagePickerResult(uri)
                }
            })

        takePicture = activity.activityResultRegistry.register("takePicture_$uniqueKey",
            owner, ActivityResultContracts.TakePicture(),
            { success ->
                if (success) {
                    onImagePickerResult(getCameraCacheUri())
                }
            })
    }

    /**
     * 弹出对话框提示
     */
    fun show() {
        val alertDialogBuilder = AlertDialog.Builder(activity)

        alertDialogBuilder.setTitle(R.string.str_image_picker)

        val options = arrayOf(
            activity.getString(R.string.str_image_picker_select_image),
            activity.getString(R.string.str_image_picker_take_picture)
        )

        alertDialogBuilder.setItems(options) { _, which ->
            when (which) {
                0 -> selectImage()
                1 -> requestPermissionAndTakePicture()
            }
        }

        alertDialogBuilder.show()
    }

    // region 缓存文件路径
    private val cameraCachePath = File(activity.cacheDir, "camera")

    private fun getCameraCacheUri(): Uri {
        if (!cameraCachePath.exists()) {
            cameraCachePath.mkdirs()
        }

        val filename = "picture.jpg"
        val file = File(cameraCachePath, filename)

        return FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file)
    }
    // endregion
}