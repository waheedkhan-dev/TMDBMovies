package com.codecollapse.tmdbmovies.models.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecollapse.tmdbmovies.common.AppConstants
import com.codecollapse.tmdbmovies.databinding.MoviesGenerLayoutBinding
import com.codecollapse.tmdbmovies.databinding.MoviesLayoutBinding
import com.codecollapse.tmdbmovies.models.datamodels.MovieDetail
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies

class MoviesGenresAdapter(mContext :Context) : ListAdapter<MovieDetail.Genres, MoviesGenresAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {
    var context = mContext
    var selectedMovie = MutableLiveData<MovieDetail>()
    inner class MyViewHolder(private val binding: MoviesGenerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(response: MovieDetail.Genres?) {

            binding.textViewMovieGenres.text = response?.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MoviesGenerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<MovieDetail.Genres>() {
            override fun areItemsTheSame(oldItem: MovieDetail.Genres, newItem: MovieDetail.Genres): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MovieDetail.Genres, newItem: MovieDetail.Genres): Boolean {
                return oldItem == newItem
            }
        }
    }
}