package com.example.dqddu.list.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dqddu.databinding.ItemMovieIntroductionBinding

class MovieIntroductionAdapter(
    private val movie: Movie,
    private val callback: (Movie) -> Unit
) : RecyclerView.Adapter<MovieIntroductionAdapter.VH>() {

    override fun getItemCount() = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        ItemMovieIntroductionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { VH(it) }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(movie, callback)

    class VH(
        private val binding: ItemMovieIntroductionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, callback: (Movie) -> Unit) =
            with(binding) {
                tvTitle.text = "简介"
                tvContent.text = movie.intro
                this.root.setOnClickListener { callback(movie) }
            }
    }
}
