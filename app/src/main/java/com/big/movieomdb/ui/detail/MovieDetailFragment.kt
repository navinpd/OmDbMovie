package com.big.movieomdb.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.big.movieomdb.R
import com.big.movieomdb.ui.CommonViewModel
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

@AndroidEntryPoint
open class MovieDetailFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var mViewModel: CommonViewModel

    companion object {
        const val TAG = "MovieDetailFragment"
        const val DETAIL_INFO = "MovieDetailFragment.DETAIL_INFO"

        fun newInstance(detail: String): MovieDetailFragment {
            val movieDetailFragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putString(DETAIL_INFO, detail)
            movieDetailFragment.arguments = bundle
            return movieDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_movie_details, container, false)

        if (arguments != null) {
            val detailInfo: String? = arguments!!.getString(DETAIL_INFO)
            if (!detailInfo.isNullOrEmpty()) {
                Log.d(TAG, detailInfo)
                mViewModel.getMovieDetails(detailInfo)
            }
        }

        mViewModel.getMovieDetails().observe(viewLifecycleOwner, Observer {
            director.text = "DIRECTOR: " + it.Director
            writer.text = "WRITER: " + it.Writer
            actor.text = "ACTORS: " + it.Actors
            synopsis.text = "SYNOPSIS: " + it.Plot
            year.text = "YEAR: " + it.Year
            title.text = "TITLE: " + it.Title
            rating.text = "Rating: " + it.imdbRating
            categories.text = "Score " + it.Metascore
            glide.load(it.Poster)
                .centerCrop()
                .error(R.drawable.ic_error_outline_black_24dp)
                .placeholder(R.drawable.ic_cloud_download_black_24dp)
                .addListener(object : RequestListener<Drawable?> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }).into(poster_image)
        })
        return root
    }

}