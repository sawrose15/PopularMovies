package com.sawrose.popularmovies.data.remote

import com.sawrose.popularmovies.model.MovieDetail
import com.sawrose.popularmovies.model.MovieResponse
import com.sawrose.popularmovies.model.VideoResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class MovieAPI(retrofit: Retrofit) {
    private val movieService: MovieService

    init {
        movieService = retrofit.create(MovieService::class.java)
    }

    fun getPopularMovies(page: Int): Single<MovieResponse> {
        return movieService.getPopularMovies(page)
    }

    fun getTopRatedMovies(page: Int): Single<MovieResponse> {
        return movieService.getTopRatedMovies(page)
    }

    fun getMovieDetail(id: Int): Single<MovieDetail> {
        return movieService.getMovie(id)
    }

    fun getVideos(id: Int): Single<VideoResponse> {
        return movieService.getVideos(id)
    }


    internal interface MovieService {
        @GET("movie/popular") fun getPopularMovies(@Query("page") page: Int): Single<MovieResponse>

        @GET("movie/top_rated") fun getTopRatedMovies(@Query("page") page: Int): Single<MovieResponse>

        @GET("movie/{movieId}") fun getMovie(@Path("movieId") movieId: Int): Single<MovieDetail>

        @GET("movie/{movieId}/videos") fun getVideos(@Path("movieId") movieId: Int): Single<VideoResponse>
    }
}