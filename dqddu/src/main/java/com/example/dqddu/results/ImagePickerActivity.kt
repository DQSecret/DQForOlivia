package com.example.dqddu.results

import android.os.Bundle
import android.util.Log
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityImagePickerBinding

class ImagePickerActivity : BaseBindingActivity<ActivityImagePickerBinding>() {

    override fun initBinding() = ActivityImagePickerBinding.inflate(layoutInflater)

    private lateinit var imagePicker: ImagePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePicker = ImagePicker(this) { uri ->
            Log.d("DQ", "onCreate: ImagePicker=($uri)")
            binding.ivPhoto.setImageURI(uri)
        }

        binding.btnImagePicker.setOnClickListener {
            imagePicker.show()
        }
    }
}
