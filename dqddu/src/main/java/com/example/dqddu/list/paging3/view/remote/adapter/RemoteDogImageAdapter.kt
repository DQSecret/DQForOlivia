package com.example.dqddu.list.paging3.view.remote.adapter

import android.os.Build.VERSION.SDK_INT
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.metadata
import coil.size.ViewSizeResolver
import com.example.dqddu.R
import com.example.dqddu.base.BaseApp
import com.example.dqddu.databinding.VhPaging3DogImageBinding

class RemoteDogImageAdapter : PagingDataAdapter<String, RemoteDogImageAdapter.VH>(REPO_COMPARATOR) {

    companion object {

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH.getInstance(parent)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(item = getItem(position))

    class VH(val binding: VhPaging3DogImageBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun getInstance(parent: ViewGroup): VH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = VhPaging3DogImageBinding.inflate(inflater, parent, false)
                return VH(binding)
            }

            val mDogImageLoader = ImageLoader.Builder(BaseApp.app)
                .componentRegistry {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder(BaseApp.app))
                    } else {
                        add(GifDecoder())
                    }
                }
                .build()
        }

        fun bind(item: String?) {
            binding.ivDog.load(item, mDogImageLoader) {
                placeholderMemoryCacheKey(binding.ivDog.metadata?.memoryCacheKey)
                size(ViewSizeResolver(binding.ivDog))
                placeholder(R.drawable.ic_dog_holder)
                error(R.drawable.ic_dog_holder)
                crossfade(true)
            }
        }
    }
}
