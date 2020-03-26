package com.kkapp.kkmovies.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kkapp.kkmovies.data.api.TheMoviedbInterface
import com.kkapp.kkmovies.data.vo.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(private val appservice:TheMoviedbInterface,private val compositeDisposable:CompositeDisposable) {

    private val _Networkstate= MutableLiveData<networkState>()
    val NetworkState:LiveData<networkState>
        get() = _Networkstate

    private val _downloadedMovieDetailsResponse= MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsResponse:LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId :Int){
        _Networkstate.postValue(networkState.LOADING)
        try {
            compositeDisposable.add(
                appservice.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _Networkstate.postValue(networkState.LOADED)
                        },
                        {
                            _Networkstate.postValue(networkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource",e.message)
        }

    }
}