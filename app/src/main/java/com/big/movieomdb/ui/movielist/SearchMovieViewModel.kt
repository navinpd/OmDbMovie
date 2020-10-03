package com.big.movieomdb.ui.movielist

import android.content.SharedPreferences
import com.big.movieomdb.data.Repository
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository
) {

    fun getSearchResults() = repository.movieSearchResult


    fun getSearchResult(query: String, pageNumber: Int) {
        repository.getServerResponse(query, pageNumber)
    }

}