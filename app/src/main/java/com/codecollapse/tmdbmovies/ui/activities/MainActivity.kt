package com.codecollapse.tmdbmovies.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.codecollapse.tmdbmovies.common.AppConstants
import com.codecollapse.tmdbmovies.common.CommonFunctions.launchActivity
import com.codecollapse.tmdbmovies.databinding.ActivityMainBinding
import com.codecollapse.tmdbmovies.models.adapter.TopRatedMoviesAdapter
import com.codecollapse.tmdbmovies.models.adapter.TrendingMoviesAdapter
import com.codecollapse.tmdbmovies.models.adapter.UpComingMoviesAdapter
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies
import com.codecollapse.tmdbmovies.models.datasources.utils.Status
import com.codecollapse.tmdbmovies.ui.viewmodel.StartupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val startupViewModel: StartupViewModel by viewModels()
    private lateinit var trendingMoviesAdapter: TrendingMoviesAdapter
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter
    private lateinit var upComingMoviesAdapter: UpComingMoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trendingNowRecyclerView.let { recyclerView ->
            trendingMoviesAdapter = TrendingMoviesAdapter(this)
            recyclerView.layoutManager =
                GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = trendingMoviesAdapter
        }

        binding.topRatedRecyclerView.let { recyclerView ->
            topRatedMoviesAdapter = TopRatedMoviesAdapter(this)
            recyclerView.layoutManager =
                GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = topRatedMoviesAdapter
        }
        binding.upComingRecyclerView.let { recyclerView ->
            upComingMoviesAdapter = UpComingMoviesAdapter(this)
            recyclerView.layoutManager =
                GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = upComingMoviesAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                    startupViewModel.getUpComingMovies().collect{
                        when (it.status) {
                            Status.LOADING -> {
                                Log.d(TAG, "Loading ....: ")
                            }
                            Status.SUCCESS -> {
                                if (!it.data.isNullOrEmpty()) {
                                    upComingMoviesAdapter.submitList(it.data as  ArrayList<TMDBMovies.Results>)
                                }
                            }
                            Status.ERROR -> {
                                Log.d(TAG, "Error: ${it.message}")
                            }
                        }
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                startupViewModel.getTrendingMovies().collect {
                    when (it.status) {
                        Status.LOADING -> {
                            Log.d(TAG, "Loading ....: ")
                        }
                        Status.SUCCESS -> {
                            if (!it.data.isNullOrEmpty()) {
                                var path = AppConstants.LOAD_BACK_DROP_BASE_URL + it.data.first().poster_path
                                Glide.with(this@MainActivity).load(path).into(binding.imageViewCurrent)
                                trendingMoviesAdapter.submitList(it.data as ArrayList<TMDBMovies.Results>)
                                Log.d(TAG, "Success: ${it.data?.get(0)?.overview}")
                            }
                        }
                        Status.ERROR -> {
                            Log.d(TAG, "Error: ${it.message}")
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                startupViewModel.getTopRatedMovies().collect {
                    when (it.status) {
                        Status.LOADING -> {
                            Log.d(TAG, "Loading ....: ")
                        }
                        Status.SUCCESS -> {
                            if (!it.data.isNullOrEmpty()) {
                                topRatedMoviesAdapter.submitList(it.data as ArrayList<TMDBMovies.Results>)
                                Log.d(TAG, "Success: ${it.data?.get(0)?.overview}")
                            }

                        }
                        Status.ERROR -> {
                            Log.d(TAG, "Error: ${it.message}")
                        }
                    }
                }
            }
        }

        trendingMoviesAdapter.selectedMovie.observe(this, Observer {
            launchActivity<DetailActivity>() {
                putExtra("movieId", it.id)
            }
        })

        topRatedMoviesAdapter.selectedMovie.observe(this, Observer {
            launchActivity<DetailActivity>() {
                putExtra("movieId", it.id)
            }
        })

        upComingMoviesAdapter.selectedMovie.observe(this, Observer {
            launchActivity<DetailActivity>() {
                putExtra("movieId", it.id)
            }
        })
    }
}