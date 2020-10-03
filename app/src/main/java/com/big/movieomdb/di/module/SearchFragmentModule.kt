package com.big.movieomdb.di.module


import android.content.Context
import com.big.movieomdb.di.ActivityContext
import com.big.movieomdb.ui.movielist.SearchMovieFragment
import dagger.Module
import dagger.Provides

@Module
class SearchFragmentModule(private val fragment: SearchMovieFragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!
}
