package com.kkapp.kkmovies.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.kkapp.kkmovies.data.api.FIRST_PAGE
import com.kkapp.kkmovies.data.api.TheMoviedbInterface
import com.kkapp.kkmovies.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiservice :TheMoviedbInterface,private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE

    val NetworkState: MutableLiveData<networkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        NetworkState.postValue(networkState.LOADING)

        compositeDisposable.add(
            apiservice.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movielist, null, page+1)
                        NetworkState.postValue(networkState.LOADED)
                    },
                    {
                        NetworkState.postValue(networkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        NetworkState.postValue(networkState.LOADING)

        compositeDisposable.add(
            apiservice.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.movielist, params.key+1)
                            NetworkState.postValue(networkState.LOADED)
                        }
                        else{
                            NetworkState.postValue(networkState.ENDOFLIST)
                        }
                    },
                    {
                        NetworkState.postValue(networkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }
}