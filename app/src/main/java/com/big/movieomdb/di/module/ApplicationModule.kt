package com.big.movieomdb.di.module

import android.content.Context
import android.content.SharedPreferences
import com.big.movieomdb.BuildConfig
import com.big.movieomdb.MyApplication
import com.big.movieomdb.data.remote.NetworkService
import com.big.movieomdb.data.remote.Networking
import com.big.movieomdb.di.ApplicationContext
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {


    @Provides
    @ApplicationContext
    fun provideContext(): Context = application.applicationContext


    @Provides
    @Singleton
    fun provideGlide() : RequestManager = Glide.with(application)

    @Provides
    @Singleton
    fun provideNetworking(): NetworkService {
        return Networking.createNetworking(
            BuildConfig.API_Key,
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences =
        application.getSharedPreferences("Local-Shared-Pref", 0)

}
