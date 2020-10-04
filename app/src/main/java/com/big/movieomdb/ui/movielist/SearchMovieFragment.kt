package com.big.movieomdb.ui.movielist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import com.big.movieomdb.ui.MainActivity
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class SearchMovieFragment : Fragment(), INextPage {

    companion object {
        fun newInstance() = SearchMovieFragment()
    }

    @Inject
    lateinit var mViewModel: SearchMovieViewModel

    @Inject
    lateinit var mGlide: RequestManager

    private lateinit var mSearchView: SearchView
    private lateinit var mMovieAdapter: MovieAdapter

    private lateinit var mSearchPlate: EditText

    private lateinit var mMovieRV: RecyclerView
    private var currentMovie: String = ""

    private var mPageNumber: Int = 1

    private var totalPageCount: Int = 1

    private var mMovieResultList = mutableListOf<Search>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
         val root = inflater.inflate(R.layout.fragment_movie_list, container, false)
        setUpView(root)

        mSearchPlate.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (v.text.toString().isNotEmpty()) {
                    searchMovie(v.text.toString())
                }
            }
            false
        }

        mViewModel.getSearchResults().observe(viewLifecycleOwner, Observer {
            progress_circle.visibility = View.GONE

            if (it != null && it.totalResults!! > 0) {
                mMovieResultList.addAll(it.Search as List)
                mMovieAdapter.notifyDataSetChanged()
                text_home.visibility = View.GONE
                totalPageCount = it.totalResults!!
                mPageNumber++

            } else if (it != null && it.totalResults!! == 0 && root != null) {

                Toast.makeText(this.context, "No Movie Results Found", Toast.LENGTH_LONG).show()
            } else if (root != null) {

                Toast.makeText(this.context, "Network Error", Toast.LENGTH_LONG).show()
            }
        })

        return root
    }



    private fun searchMovie(movieName: String) {
        progress_circle.visibility = View.VISIBLE
        text_home.visibility = View.VISIBLE

        mMovieResultList.clear()
        mPageNumber = 1

        currentMovie = movieName

        mViewModel.getSearchResult(movieName, mPageNumber)

        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mSearchPlate.windowToken, 0)
    }

    private fun setUpView(view: View) {
        mSearchView = view.findViewById(R.id.search_sv)
        mSearchPlate = mSearchView.findViewById(R.id.search_src_text)

        mMovieRV = view.findViewById(R.id.movie_item_list)
        mMovieRV.layoutManager = GridLayoutManager(context, 2)
        mMovieAdapter = MovieAdapter(mMovieResultList, mGlide)
        mMovieRV.adapter = mMovieAdapter
        mMovieAdapter.requestForNextItem = this
        mMovieAdapter.setOnItemClickListener(selectItemClickListener)
    }

    private val selectItemClickListener =
        View.OnClickListener { view ->
            val selectedCity = view.tag as String

            (activity as MainActivity).launchMovieDetails(selectedCity)

//            search_sv.setQuery("", true)
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
        if (mPageNumber < totalPageCount) {
            mPageNumber++
            progress_circle.visibility = View.VISIBLE
            mViewModel.getSearchResult(currentMovie, mPageNumber)
        }
    }
}

//Interface to invoke query for next set of Items
interface INextPage {
    fun loadNextPage()
}