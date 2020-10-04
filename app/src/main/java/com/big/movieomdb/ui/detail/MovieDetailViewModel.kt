package com.big.movieomdb.ui.detail

import com.big.movieomdb.data.Repository
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val repository: Repository
) {
    fun getSearchResults() = repository.mMovieDetail


    fun getMovieDetails(info: String) {
        repository.getMovieDetails(info)
    }
}