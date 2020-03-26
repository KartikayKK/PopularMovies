package com.kkapp.kkmovies.ui.singlemoviedetails

import androidx.lifecycle.LiveData
import com.kkapp.kkmovies.data.api.TheMoviedbInterface
import com.kkapp.kkmovies.data.repository.MovieDetailsNetworkDataSource
import com.kkapp.kkmovies.data.repository.networkState
import com.kkapp.kkmovies.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService:TheMoviedbInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<networkState> {
        return movieDetailsNetworkDataSource.NetworkState
    }


}