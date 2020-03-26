package com.kkapp.kkmovies.ui.singlemoviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kkapp.kkmovies.data.repository.networkState
import com.kkapp.kkmovies.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<networkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}