package com.big.movieomdb.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.big.movieomdb.BuildConfig
import com.big.movieomdb.data.remote.NetworkService
import com.big.movieomdb.data.remote.response.moviedetail.MovieDetails
import com.big.movieomdb.data.remote.response.searchresult.RootSearch
import retrofit2.Callback
import javax.inject.Inject


open class Repository @Inject constructor(private val networkService: NetworkService) {
    private val TAG = "Repository"
    val mMovieSearchResult: MutableLiveData<RootSearch> = MutableLiveData()
    val mMovieDetail: MutableLiveData<MovieDetails> = MutableLiveData()

    fun getMovieList(query: String, pageNumber: Int) {
        val data = networkService.getMovieList(BuildConfig.API_Key, query, pageNumber)

        data.enqueue(object : Callback<RootSearch> {
            override fun onResponse(
                call: retrofit2.Call<RootSearch>,
                response: retrofit2.Response<RootSearch>
            ) {

                if (!response.isSuccessful || response.code() != 200) {
                    mMovieSearchResult.value = null
                } else {
                    mMovieSearchResult.value = response.body()
                }
            }

            override fun onFailure(call: retrofit2.Call<RootSearch>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
                mMovieSearchResult.value = null
            }
        })
    }

    fun getMovieDetails(query: String) {
        val data = networkService.getMovieDetails(BuildConfig.API_Key, query)

        data.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(
                call: retrofit2.Call<MovieDetails>,
                response: retrofit2.Response<MovieDetails>
            ) {

                if (!response.isSuccessful || response.code() != 200) {
                    mMovieDetail.value = null
                } else {
                    mMovieDetail.value = response.body()
                }
            }

            override fun onFailure(call: retrofit2.Call<MovieDetails>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
                mMovieDetail.value = null
            }
        })
    }


}