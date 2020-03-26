package com.kkapp.kkmovies.data.api

import com.kkapp.kkmovies.data.vo.MovieDetails
import com.kkapp.kkmovies.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMoviedbInterface
{
    //https://api.themoviedb.org/3/movie/39538?api_key=3c56657daf96b3590adbc0bb5d5b83bb&language=en-US
    //https://api.themoviedb.org/3/movie/popular?api_key=3c56657daf96b3590adbc0bb5d5b83bb&language=en-US
    //https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int) : Single<MovieResponse>


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id :Int) : Single<MovieDetails>
}