package com.big.movieomdb.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.big.movieomdb.BuildConfig
import com.big.movieomdb.data.remote.NetworkService
import com.big.movieomdb.data.remote.response.searchresult.RootSearch
import retrofit2.Callback
import javax.inject.Inject


open class Repository @Inject constructor(private val networkService: NetworkService) {
    private val TAG = "Repository"
    val movieSearchResult: MutableLiveData<RootSearch> = MutableLiveData()

    public fun getServerResponse(query: String, pageNumber: Int) {
        val data = networkService.getMovieList(BuildConfig.API_Key, query, pageNumber)

        data.enqueue(object : Callback<RootSearch> {
            override fun onResponse(
                call: retrofit2.Call<RootSearch>,
                response: retrofit2.Response<RootSearch>
            ) {

                if (!response.isSuccessful || response.code() != 200) {
                    movieSearchResult.setValue(null)
                } else {
                    movieSearchResult.setValue(response.body())
                }
            }

            override fun onFailure(call: retrofit2.Call<RootSearch>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
                movieSearchResult.setValue(null)
            }
        });
    }
}