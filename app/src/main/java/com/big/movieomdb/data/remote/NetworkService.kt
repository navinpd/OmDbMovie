package com.big.movieomdb.data.remote

import com.big.movieomdb.BuildConfig
import com.big.movieomdb.data.remote.response.moviedetail.MovieDetails
import com.big.movieomdb.data.remote.response.searchresult.RootSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {


    // https://www.omdbapi.com/?apikey=b9bd48a6&s=Marvel&type=movie
    @GET(BuildConfig.BASE_URL)
    fun getMovieList(
        @Query(Endpoints.KEY_API_KEY) apiKey: String = Networking.API_VAL,
        @Query(Endpoints.KEY_QUERY) queryText: String,
        @Query(Endpoints.PAGE_NUM) numOfResults: Int
    ): Call<RootSearch>
    // {
    //  "Search": [
    //    {
    //      "Title": "Captain Marvel",
    //      "Year": "2019",
    //      "imdbID": "tt4154664",
    //      "Type": "movie",
    //      "Poster": "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_SX300.jpg"
    //    }
    //  ],
    //  "totalResults": "112",
    //  "Response": "True"
    //}



    // https://www.omdbapi.com/?apikey=b9bd48a6&i=tt4154664
    @GET(BuildConfig.BASE_URL)
    fun getMovieDetails(
        @Query(Endpoints.KEY_API_KEY) apiKey: String = Networking.API_VAL,
        @Query(Endpoints.IMDB_ID) queryText: String,
        @Query(Endpoints.KEY_FORMAT) format: String = Endpoints.KEY_JSON_FORMAT
    ): Call<MovieDetails>
    //{
    //  "Title": "Captain Marvel",
    //  "Year": "2019",
    //  "Rated": "PG-13",
    //  "Released": "08 Mar 2019",
    //  "Runtime": "123 min",
    //  "Genre": "Action, Adventure, Sci-Fi",
    //  "Director": "Anna Boden, Ryan Fleck",
    //  "Writer": "Anna Boden (screenplay by), Ryan Fleck (screenplay by), Geneva Robertson-Dworet (screenplay by), Nicole Perlman (story by), Meg LeFauve (story by), Anna Boden (story by), Ryan Fleck (story by), Geneva Robertson-Dworet (story by)",
    //  "Actors": "Brie Larson, Samuel L. Jackson, Ben Mendelsohn, Jude Law",
    //  "Plot": "Carol Danvers becomes one of the universe's most powerful heroes when Earth is caught in the middle of a galactic war between two alien races.",
    //  "Language": "English",
    //  "Country": "USA, Australia",
    //  "Awards": "7 wins & 46 nominations.",
    //  "Poster": "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_SX300.jpg",
    //  "Ratings": [
    //    {
    //      "Source": "Metacritic",
    //      "Value": "64/100"
    //    }
    //  ],
    //  "Metascore": "64",
    //  "imdbRating": "6.9",
    //  "imdbVotes": "423,584",
    //  "imdbID": "tt4154664",
    //  "Type": "movie",
    //  "DVD": "N/A",
    //  "BoxOffice": "N/A",
    //  "Production": "Marvel Studios",
    //  "Website": "N/A",
    //  "Response": "True"
    //}

}