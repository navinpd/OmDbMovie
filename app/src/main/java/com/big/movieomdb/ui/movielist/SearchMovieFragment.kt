package com.big.movieomdb.ui.movielist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.big.movieomdb.MyApplication
import com.big.movieomdb.R
import com.big.movieomdb.data.remote.response.searchresult.Search
import com.big.movieomdb.di.component.DaggerFragmentComponent
import com.big.movieomdb.di.module.SearchFragmentModule
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class SearchMovieFragment : Fragment(), INextPage {

    companion object {
        fun newInstance() = SearchMovieFragment()
    }

    @Inject
    lateinit var mViewModel: SearchMovieViewModel

    @Inject
    lateinit var glide: RequestManager

    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var movieAdapter: MovieAdapter

//    private lateinit var textView: TextView
    private lateinit var searchPlate: EditText

    private lateinit var movieRV: RecyclerView
    private var currentQuery: String = ""

    private var pageNumber: Int = 1

    private var totalPageCount: Int = 1

    private var listOfMovieResult = mutableListOf<Search>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
         val root = inflater.inflate(R.layout.fragment_movie_list, container, false)
        setUpView(root)

        searchPlate.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (v.text.toString().isNotEmpty()) {
                    searchMovie(v.text.toString())
                }
            }
            false
        }

        mViewModel.getSearchResults().observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE

            if (it != null && it.totalResults!! > 0) {
                listOfMovieResult.addAll(it.Search as List)
                movieAdapter.notifyDataSetChanged()
//                textView.visibility = View.GONE
                totalPageCount = it.totalResults!!
                pageNumber++

            } else if (it != null && it.totalResults!! == 0 && root != null) {

                Toast.makeText(this.context, "No Movie Results Found", Toast.LENGTH_LONG).show()
            } else if (root != null) {

                Toast.makeText(this.context, "Network Error", Toast.LENGTH_LONG).show()
            }
        })


        return root
    }



    private fun searchMovie(movieName: String) {
        progressBar.visibility = View.VISIBLE
//        textView.visibility = View.VISIBLE

        listOfMovieResult.clear()
        pageNumber = 1

        currentQuery = movieName

        mViewModel.getSearchResult(movieName, pageNumber)

        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchPlate.windowToken, 0)
    }

    private fun setUpView(view: View) {

//        textView = view.findViewById(R.id.text_home)
        searchView = view.findViewById(R.id.search_sv)
        progressBar = view.findViewById(R.id.progress_circle)
        searchPlate = searchView.findViewById(R.id.search_src_text)

        movieRV = view.findViewById(R.id.movie_item_list)
        movieRV.layoutManager = GridLayoutManager(context, 1)
        movieAdapter = MovieAdapter(listOfMovieResult, glide)
        movieRV.adapter = movieAdapter
        movieAdapter.requestForNextItem = this

    }

    private fun getDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent(
                (context!!
                    .applicationContext as MyApplication).applicationComponent
            )
            .searchFragmentModule(SearchFragmentModule(this))
            .build()
            .injectSearch(this)
    }

    override fun loadNextPage() {
        if (pageNumber < totalPageCount) {
            pageNumber++
            progressBar.visibility = View.VISIBLE
            mViewModel.getSearchResult(currentQuery, pageNumber)
        }
    }
}

//Interface to invoke query for next set of MovieList
interface INextPage {
    fun loadNextPage()
}