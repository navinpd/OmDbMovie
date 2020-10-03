package com.big.movieomdb.di.component

import android.content.Context
import android.content.SharedPreferences
import com.big.movieomdb.MyApplication
import com.big.movieomdb.data.remote.NetworkService
import com.big.movieomdb.di.ApplicationContext
import com.big.movieomdb.di.module.ApplicationModule
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    @ApplicationContext
    fun getContext() : Context

    fun getNetworkService() : NetworkService

    fun getPrefStore() : SharedPreferences

    fun getGlide() : RequestManager

}