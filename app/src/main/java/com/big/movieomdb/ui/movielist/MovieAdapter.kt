package com.big.movieomdb.ui.movielist

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.big.movieomdb.R
import com.big.movieomdb.data.remote.response.searchresult.Search
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class MovieAdapter(
    private var mMovieListItem: MutableList<Search>,
    private var mGlide: RequestManager
) : RecyclerView.Adapter<MovieAdapter.DataViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun getItemCount() = mMovieListItem.size
    lateinit var requestForNextItem: INextPage

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(mMovieListItem[position])
        if (mMovieListItem.isNotEmpty() && position == mMovieListItem.size - 1) {
            requestForNextItem.loadNextPage()
        }
    }

    fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
        onClickListener = itemClickListener
    }

    inner class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val selectableImage: ImageView = view.findViewById(R.id.poster_image)
        private val progressCircle: ProgressBar = view.findViewById(R.id.progress_circle)
        private val releaseDate: TextView = view.findViewById(R.id.release_date)
        private val title: TextView = view.findViewById(R.id.title)
        private val overview: TextView = view.findViewById(R.id.overview)
        private val errorImageUrl: TextView = view.findViewById(R.id.error_image_url)

        fun bind(searchResult: Search) {

            itemView.tag = searchResult.imdbID
            itemView.setOnClickListener(onClickListener)

            releaseDate.text = "Release Date: " + searchResult.Year
            title.text = "Title: " + searchResult.Title
            overview.text = "Type: " + searchResult.Type
            errorImageUrl.text = searchResult.Poster

            mGlide.load(errorImageUrl.text.toString())
                .centerCrop()
                .error(R.drawable.ic_error_outline_black_24dp)
                .placeholder(R.drawable.ic_cloud_download_black_24dp)
                .addListener(object : RequestListener<Drawable?> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressCircle.visibility = View.GONE
                        errorImageUrl.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressCircle.visibility = View.GONE
                        errorImageUrl.visibility = View.GONE
                        return false
                    }
                })
                .into(selectableImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_poster_card, parent, false)
    )

}