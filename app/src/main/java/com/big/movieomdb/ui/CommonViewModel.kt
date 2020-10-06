package com.big.movieomdb.ui

import androidx.lifecycle.ViewModel
import com.big.movieomdb.data.Repository
import javax.inject.Inject

class CommonViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getSearchResults() = repository.mMovieSearchResult

    fun getMovieDetails() = repository.mMovieDetail

    fun getSearchResult(query: String, pageNumber: Int) {
        repository.getMovieList(query, pageNumber)
    }

    fun getMovieDetails(info: String) {
        repository.getMovieDetails(info)
    }

}