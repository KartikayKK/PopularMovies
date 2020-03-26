package com.kkapp.kkmovies.ui.singlemoviedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.kkapp.kkmovies.R
import com.kkapp.kkmovies.data.api.POSTER_BASE_URL
import com.kkapp.kkmovies.data.api.TheMovieDbClient
import com.kkapp.kkmovies.data.api.TheMoviedbInterface
import com.kkapp.kkmovies.data.repository.networkState
import com.kkapp.kkmovies.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_singlemovie.*
import java.text.NumberFormat
import java.util.*

class singlemovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository:MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singlemovie)
        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : TheMoviedbInterface = TheMovieDbClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, androidx.lifecycle.Observer { bindUI(it)
        })



        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = if (it == networkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == networkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);


    }


    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}