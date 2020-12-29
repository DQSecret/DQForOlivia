package com.example.dqddu.list.concat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dqddu.databinding.VhConcatMovieActorsBinding

class MovieActorsAdapter(
    movie: Movie,
    private val callback: (Movie.Actor) -> Unit
) : ListAdapter<Movie.Actor, MovieActorsAdapter.VH>(Movie.Actor.DiffCallback) {

    init {
        submitList(movie.actors)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VhConcatMovieActorsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { VH(it) }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(position, getItem(position), callback)

    class VH(
        private val binding: VhConcatMovieActorsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int, actor: Movie.Actor, callback: (Movie.Actor) -> Unit) =
            with(binding) {
                tvName.text = "$position. ${actor.name} - ${actor.id}"
                tvRole.text = "$position. ${actor.roleFormat()} - ${actor.id}"
                this.root.setOnClickListener { callback(actor) }
            }
    }
}
