package com.kkapp.kkmovies.ui.PopularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kkapp.kkmovies.data.api.POST_PER_PAGE
import com.kkapp.kkmovies.data.api.TheMoviedbInterface
import com.kkapp.kkmovies.data.repository.MovieDataSource
import com.kkapp.kkmovies.data.repository.MovieDataSourceFactory
import com.kkapp.kkmovies.data.repository.networkState
import com.kkapp.kkmovies.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagelistRepository (private val apiService : TheMoviedbInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<networkState> {
        return Transformations.switchMap<MovieDataSource, networkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::NetworkState)
    }
}