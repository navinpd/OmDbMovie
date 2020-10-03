package com.big.movieomdb.di.component

import com.big.movieomdb.di.FragmentScope
import com.big.movieomdb.di.module.DetailsFragmentModule
import com.big.movieomdb.di.module.SearchFragmentModule
import com.big.movieomdb.ui.detail.MovieDetailFragment
import com.big.movieomdb.ui.movielist.SearchMovieFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [DetailsFragmentModule::class, SearchFragmentModule::class])
interface FragmentComponent {

    fun injectDetails(fragment: MovieDetailFragment)

    fun injectSearch(fragment: SearchMovieFragment)
}
