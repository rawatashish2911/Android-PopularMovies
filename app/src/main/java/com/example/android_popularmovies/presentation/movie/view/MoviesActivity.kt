package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_popularmovies.presentation.movie.adaptor.MoviesAdapter
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.view.ui.theme.AndroidPopularMoviesTheme
import com.example.android_popularmovies.presentation.movie.view_model.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {
    private val viewModel: MovieListViewModel by viewModels()

    private var moviesAdapter: MoviesAdapter = MoviesAdapter { id ->
        viewModel.onDetailTap(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setContent {
            AndroidPopularMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun initialize() {
        setRecyclerView()
        fetchMovies()
        handleError()
        handleResult()
        handleOnDetailTap()
    }

    private fun handleOnDetailTap() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.onTapDetailState.collectLatest { id ->
//                    val navController = binding.root.findNavController()
//                    navController
//                        .navigate(
//                            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
//                                id
//                            )
//                        )
//                }
            }
        }
    }

    private fun setRecyclerView() {
//        binding.recyclerView.apply {
//            setHasFixedSize(true)
//            layoutManager = GridLayoutManager(activity, MovieListFragment.movieGridSpanCount)
//            adapter = moviesAdapter
//        }
        setUpSearch()
    }

    private fun fetchMovies() {
        viewModel.fetchMoviesList()
    }

    private fun setUpSearch() {
//        binding.searchBar.addTextChangedListener { editable ->
//            editable?.run {
//                adapter.updateMovies(viewModel.filterMovies(toString()))
//            }
//        }
    }

    private fun handleError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorState.collect { message ->
                    Toast.makeText(
                        applicationContext,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun handleResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MovieListState.Error -> {
//                            binding.progressBar.hideVisibility()
                            Timber.e(state.error)
                        }
                        MovieListState.Loading -> {
//                            binding.progressBar.showVisibility()
                        }
                        is MovieListState.Success -> {
//                            binding.progressBar.hideVisibility()
//                            binding.recyclerView.showVisibility()
                            moviesAdapter.updateMovies(state.movies)
                        }
                    }
                }
            }
        }
    }


    companion object {
        const val movieGridSpanCount: Int = 2

    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AndroidPopularMoviesTheme {
            Greeting("Android")
        }

    }
}