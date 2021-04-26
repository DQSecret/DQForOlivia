package com.example.dqddu.list.paging3.view.loader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dqddu.R

class LoaderStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    class LoaderViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

        companion object {

            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.vh_paging3_dog_image_loader, parent, false)
                return LoaderViewHolder(view, retry)
            }
        }

        private val motionLayout: MotionLayout = view.findViewById(R.id.motion)

        init {
            view.findViewById<Button>(R.id.btn_retry).setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                motionLayout.transitionToEnd()
            } else {
                motionLayout.transitionToStart()
            }
        }
    }
}