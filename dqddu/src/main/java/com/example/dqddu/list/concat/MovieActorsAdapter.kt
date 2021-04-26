package com.example.dqddu.list.concat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dqddu.databinding.VhConcatMovieActorsBinding

class MovieActorsAdapter(
    private val movie: Movie,
    private val callback: (Movie.Actor) -> Unit
) : RecyclerView.Adapter<MovieActorsAdapter.VH>() {

    override fun getItemCount() = movie.actors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VhConcatMovieActorsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { VH(it) }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(position, movie.actors[position], callback)

    class VH(
        private val binding: VhConcatMovieActorsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int, actor: Movie.Actor, callback: (Movie.Actor) -> Unit) =
            with(binding) {
                tvName.text = "${actor.name} - $position"
                tvRole.text = "${actor.roleFormat()} - $position"
                this.root.setOnClickListener { callback(actor) }
            }
    }
}
