package com.big.movieomdb.di

import android.content.Context
import android.content.SharedPreferences
import com.big.movieomdb.BuildConfig
import com.big.movieomdb.data.Repository
import com.big.movieomdb.data.remote.NetworkService
import com.big.movieomdb.data.remote.Networking
import com.big.movieomdb.ui.CommonViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DiModule {

    @Provides
    fun provideGlide(@ApplicationContext appContext: Context): RequestManager =
        Glide.with(appContext)

    @Provides
    fun provideNetworking(@ApplicationContext appContext: Context): NetworkService {
        return Networking.createNetworking(
            BuildConfig.API_Key,
            BuildConfig.BASE_URL,
            appContext.cacheDir,
            10 * 1024 * 1024
        )
    }

    @Provides
    fun provideSharedPreference(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("Local-Shared-Pref", 0)

    @Provides
    fun provideSearchViewModel(repository: Repository): CommonViewModel =
        CommonViewModel(repository)
}