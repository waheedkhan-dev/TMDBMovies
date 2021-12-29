package com.codecollapse.tmdbmovies.models.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codecollapse.tmdbmovies.R
import com.codecollapse.tmdbmovies.common.AppConstants
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies

class MoviesAdapter(mContext : Context)  :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    private var TAG = "MoviesAdapter"

    private var movieList = ArrayList<TMDBMovies.Results>()
    var selectedMovie = MutableLiveData<TMDBMovies.Results>()
    var context = mContext


    fun submitData(_movieList: ArrayList<TMDBMovies.Results>) {
        movieList = _movieList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewMovieName = itemView.findViewById<TextView>(R.id.textViewMovieName)!!
        var imageViewMovieCover = itemView.findViewById<ImageView>(R.id.imageViewMovieCover)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.movies_layout, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        if (movieList.size > 0) {
            return movieList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var movie = movieList[position]
        var path = AppConstants.LOAD_BACK_DROP_BASE_URL + movie.backdrop_path
        Glide
            .with(context)
            .load(path)
            .centerCrop()
            .into(holder.imageViewMovieCover)

        holder.textViewMovieName.text = movie.title
        holder.imageViewMovieCover.setOnClickListener {
            selectedMovie.postValue(movie)
        }
    }
}