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
import com.codecollapse.tmdbmovies.databinding.MovieCastLayoutBinding
import com.codecollapse.tmdbmovies.databinding.MoviesLayoutBinding
import com.codecollapse.tmdbmovies.models.datamodels.MovieCredits
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies

class MovieCastAdapter(mContext :Context) : ListAdapter<MovieCredits.MovieCast, MovieCastAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {
    var context = mContext
    var selectedMovie = MutableLiveData<MovieCredits.MovieCast>()
    inner class MyViewHolder(private val binding: MovieCastLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(response: MovieCredits.MovieCast?) {
            var path = AppConstants.LOAD_IMAGE_BASE_URL + response?.profile_path
            binding.textViewMovieCastName.text = response?.name
            Glide
                .with(context)
                .load(path)
                .centerCrop()
                .into(binding.imageViewMovieCastPhoto)
           /* binding.textViewRating.text = response?.vote_average.toString()
            binding.imageViewMoviePosture.setOnClickListener {
                selectedMovie.postValue(response!!)
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieCastLayoutBinding.inflate(
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
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<MovieCredits.MovieCast>() {
            override fun areItemsTheSame(oldItem: MovieCredits.MovieCast, newItem: MovieCredits.MovieCast): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MovieCredits.MovieCast, newItem: MovieCredits.MovieCast): Boolean {
                return oldItem == newItem
            }
        }
    }
}