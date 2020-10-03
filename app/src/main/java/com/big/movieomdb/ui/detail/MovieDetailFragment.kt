package com.big.movieomdb.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.big.movieomdb.MyApplication
import com.big.movieomdb.R
import com.big.movieomdb.di.component.DaggerFragmentComponent
import com.big.movieomdb.di.module.DetailsFragmentModule
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    @Inject
    lateinit var mViewModel: MovieDetailViewModel

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        getDependencies()
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }


    private fun getDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent(
                (context!!
                    .applicationContext as MyApplication).applicationComponent
            )
            .detailsFragmentModule(DetailsFragmentModule(this))
            .build()
            .injectDetails(this)
    }

}