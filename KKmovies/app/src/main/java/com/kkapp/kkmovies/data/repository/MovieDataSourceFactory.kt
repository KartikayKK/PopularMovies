package com.kkapp.kkmovies.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.kkapp.kkmovies.data.api.TheMoviedbInterface
import com.kkapp.kkmovies.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : TheMoviedbInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}