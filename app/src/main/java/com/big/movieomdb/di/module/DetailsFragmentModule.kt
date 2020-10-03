package com.big.movieomdb.di.module


import android.content.Context
import com.big.movieomdb.di.ActivityContext
import com.big.movieomdb.ui.detail.MovieDetailFragment
import dagger.Module
import dagger.Provides

@Module
class DetailsFragmentModule(private val fragment: MovieDetailFragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!
}
