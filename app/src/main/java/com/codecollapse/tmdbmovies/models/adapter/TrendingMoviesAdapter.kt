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
import com.codecollapse.tmdbmovies.databinding.MoviesLayoutBinding
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies

class TrendingMoviesAdapter(mContext :Context) : ListAdapter<TMDBMovies.Results, TrendingMoviesAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {
    var context = mContext
    var selectedMovie = MutableLiveData<TMDBMovies.Results>()
    inner class MyViewHolder(private val binding: MoviesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(response: TMDBMovies.Results?) {
            var path = AppConstants.LOAD_IMAGE_BASE_URL + response?.poster_path
            binding.textViewMovieName.text = response?.title
            Glide
                .with(context)
                .load(path)
                .centerCrop()
                .into(binding.imageViewMoviePosture)
            binding.imageViewMoviePosture.setOnClickListener {
                selectedMovie.postValue(response!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MoviesLayoutBinding.inflate(
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
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<TMDBMovies.Results>() {
            override fun areItemsTheSame(oldItem: TMDBMovies.Results, newItem: TMDBMovies.Results): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: TMDBMovies.Results, newItem: TMDBMovies.Results): Boolean {
                return oldItem == newItem
            }
        }
    }
}