package api

import Model.Article
import Model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

   //https://newsapi.org/v2/everything?q=tesla&from=2023-01-21&sortBy=publishedAt&apiKey=b06deb5612934dd0969c59c507f70a5f

    @GET("everything")
    suspend fun getNewsFromQuery(
        @Query("q")
        q:String,

        @Query("sortBy")
        sortBy: String,

        @Query("apiKey")
        apiKey:String

    ): NewsModel

}

