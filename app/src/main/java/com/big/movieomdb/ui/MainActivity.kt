package com.big.movieomdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.big.movieomdb.R
import com.big.movieomdb.ui.main.SearchMovie

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SearchMovie.newInstance())
                    .commitNow()
        }
    }
}