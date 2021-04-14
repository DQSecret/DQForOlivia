package com.example.dqddu.list.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dqddu.databinding.ItemMovieBasicInfoBinding
import com.example.dqddu.ext.dp

class MovieBasicInfoAdapter(
    private val movie: Movie,
    private val callback: (Movie) -> Unit
) : RecyclerView.Adapter<MovieBasicInfoAdapter.VH>() {

    override fun getItemCount() = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        ItemMovieBasicInfoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { VH(it) }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(movie, callback)

    class VH(
        private val binding: ItemMovieBasicInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, callback: (Movie) -> Unit) =
            with(binding) {
                tvCnm.text = movie.cnm
                tvEnm.text = movie.enm
                Glide.with(ivPoster)
                    .load(movie.posterUrl)
                    .override(100.dp, 140.dp)
                    .centerCrop()
                    .into(ivPoster)
                this.root.setOnClickListener { callback(movie) }
            }
    }
}
