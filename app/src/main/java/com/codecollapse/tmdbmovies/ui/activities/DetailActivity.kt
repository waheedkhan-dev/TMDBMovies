package com.codecollapse.tmdbmovies.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.codecollapse.tmdbmovies.R
import com.codecollapse.tmdbmovies.common.AppConstants
import com.codecollapse.tmdbmovies.databinding.ActivityDetailBinding
import com.codecollapse.tmdbmovies.databinding.ActivityMainBinding
import com.codecollapse.tmdbmovies.models.adapter.MoviesGenresAdapter
import com.codecollapse.tmdbmovies.models.adapter.TrendingMoviesAdapter
import com.codecollapse.tmdbmovies.models.datasources.utils.Status
import com.codecollapse.tmdbmovies.ui.viewmodel.StartupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var moviesGenresAdapter: MoviesGenresAdapter
    private val startupViewModel : StartupViewModel by viewModels()
    private var movieId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra("movieId")){
            movieId = intent.getIntExtra("movieId",0)

            binding.movieGenresRecyclerView.let { recyclerView ->
                moviesGenresAdapter = MoviesGenresAdapter(this)
                recyclerView.layoutManager =
                    GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = moviesGenresAdapter
            }

            movieId.let {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED){
                        startupViewModel.getMovieDetails(movieId,"en").collect{
                            when(it.status){
                                Status.LOADING->{}
                                Status.SUCCESS->{
                                    var movieDetail = it.data
                                    if(movieDetail!=null){
                                        var path = AppConstants.LOAD_BACK_DROP_BASE_URL + movieDetail!!.backdrop_path
                                        Glide.with(this@DetailActivity).load(path).into(binding.imageViewPosture)
                                        binding.textViewMovieName.text = movieDetail.original_title
                                        binding.textViewOverView.text = movieDetail.overview
                                        binding.extensionLayout.textViewRating.text = "${movieDetail.vote_average}/10"
                                        binding.extensionLayout.textViewThumbUp.text = movieDetail.vote_count.toString()
                                        val hours: Int =
                                            movieDetail.runtime!!.div(60) //since both are ints, you get an int

                                        val minutes: Int = movieDetail.runtime!! % 60
                                        binding.extensionLayout.textViewDuration.text = "${hours}h ${minutes}m"
                                        moviesGenresAdapter.submitList(it.data!!.genres)
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