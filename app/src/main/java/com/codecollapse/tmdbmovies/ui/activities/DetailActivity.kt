package com.codecollapse.tmdbmovies.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.codecollapse.tmdbmovies.R
import com.codecollapse.tmdbmovies.common.AppConstants
import com.codecollapse.tmdbmovies.models.datasources.utils.Status
import com.codecollapse.tmdbmovies.ui.viewmodel.StartupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val startupViewModel : StartupViewModel by viewModels()
    private var movieId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if(intent.hasExtra("movieId")){
            movieId = intent.getIntExtra("movieId",0)
            if(movieId!=0){
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED){
                        startupViewModel.getMovieDetails(movieId,"en").collect{
                            when(it.status){
                                Status.LOADING->{}
                                Status.SUCCESS->{
                                    var movieDetail = it.data
                                    if(movieDetail!=null){
                                        var path = AppConstants.LOAD_IMAGE_BASE_URL + movieDetail!!.poster_path
                                        Glide.with(this@DetailActivity).load(path).into(imageViewMoviePhoto)
                                        textViewMovieName.text = movieDetail.original_title
                                        textViewOverView.text = movieDetail.overview
                                        textViewRating.text = "${movieDetail.vote_average}/10"
                                        movieDetail.genres.forEach { genres->
                                            textViewGenres.text = genres.name
                                            return@forEach
                                        }
                                    }else{

                                    }
                                }
                                Status.ERROR->{}
                            }
                        }
                    }
                }
            }
        }

        imageViewBack.setOnClickListener { finish() }
    }
}