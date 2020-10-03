package com.big.movieomdb.di.component


import com.big.movieomdb.di.ActivityScope
import com.big.movieomdb.di.module.ActivityModule
import com.big.movieomdb.ui.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)


}