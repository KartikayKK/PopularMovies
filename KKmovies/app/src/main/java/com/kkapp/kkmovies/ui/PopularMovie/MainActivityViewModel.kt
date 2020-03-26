package com.kkapp.kkmovies.ui.PopularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kkapp.kkmovies.data.repository.networkState
import com.kkapp.kkmovies.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel (private val movieRepository : MoviePagelistRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<networkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}