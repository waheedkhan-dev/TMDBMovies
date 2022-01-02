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
import com.codecollapse.tmdbmovies.R
import com.codecollapse.tmdbmovies.common.CommonFunctions.launchActivity
import com.codecollapse.tmdbmovies.databinding.ActivityMainBinding
import com.codecollapse.tmdbmovies.models.adapter.MoviesAdapter
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies
import com.codecollapse.tmdbmovies.models.datasources.utils.Status
import com.codecollapse.tmdbmovies.ui.viewmodel.StartupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val startupViewModel: StartupViewModel by viewModels()
    lateinit var moviesAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesRecyclerView.let { recyclerView ->
            moviesAdapter = MoviesAdapter(this)
            recyclerView.layoutManager =
                GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = moviesAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                startupViewModel.getTopRatedMovies().collect {
                    when (it.status) {
                        Status.LOADING -> {
                            Log.d(TAG, "Loading ....: ")
                        }
                        Status.SUCCESS -> {
                            if (!it.data.isNullOrEmpty()) {
                                moviesAdapter.submitList(it.data as ArrayList<TMDBMovies.Results>)
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

        moviesAdapter.selectedMovie.observe(this, Observer {
            launchActivity<DetailActivity>() {
                putExtra("movieId", it.id)
            }
        })
    }
}