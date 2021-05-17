package com.example.dqddu.results

import android.os.Bundle
import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ActivityImagePickerBinding

class ImagePickerActivity : BaseBindingActivity<ActivityImagePickerBinding>() {

    override fun initBinding() = ActivityImagePickerBinding.inflate(layoutInflater)

    private lateinit var imagePicker: ImagePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePicker = ImagePicker(this) { uri ->
            // 这么做是为了先清空之前的缓存
            // setImageURI() 内部含有判断uri是否变化的逻辑 if (!uri.equals(mUri)))) { ... }
            binding.ivPhoto.setImageURI(null)
            binding.ivPhoto.setImageURI(uri)
        }

        binding.btnImagePicker.setOnClickListener {
            imagePicker.show()
        }
    }
}
