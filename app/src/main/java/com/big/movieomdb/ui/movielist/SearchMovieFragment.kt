package com.big.movieomdb.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.big.movieomdb.MyApplication
import com.big.movieomdb.R
import com.big.movieomdb.di.component.DaggerFragmentComponent
import com.big.movieomdb.di.module.SearchFragmentModule
import javax.inject.Inject

class SearchMovieFragment : Fragment() {

    companion object {
        fun newInstance() = SearchMovieFragment()
    }

    @Inject
    lateinit var mViewModel: SearchMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getDependencies()
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }


    private fun getDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent(
                (context!!
                    .applicationContext as MyApplication).applicationComponent
            )
            .searchFragmentModule(SearchFragmentModule(this))
            .build()
            .injectSearch(this)
    }
}