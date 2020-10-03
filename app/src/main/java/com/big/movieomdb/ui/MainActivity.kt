package com.big.movieomdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.big.movieomdb.MyApplication
import com.big.movieomdb.R
import com.big.movieomdb.di.component.DaggerActivityComponent
import com.big.movieomdb.di.module.ActivityModule
import com.big.movieomdb.ui.movielist.SearchMovieFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchMovieFragment.newInstance())
                .commitNow()
        }
    }

    private fun setUpDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}