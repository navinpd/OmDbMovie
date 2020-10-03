package com.big.movieomdb.di.module

import android.content.Context
import com.big.movieomdb.di.ActivityContext
import com.big.movieomdb.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
public class ActivityModule(private val mainActivity: MainActivity) {


    @Provides
    @ActivityContext
    fun provideContext(): Context = mainActivity


}