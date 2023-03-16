package repository

import Model.Article
import Model.NewsModel
import api.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import utils.Utils.APIKEY
import javax.inject.Inject


class MainRepository @Inject constructor(val apiInterface: ApiInterface) {


    fun getModel(q:String,sort:String): Flow<NewsModel> = flow {
        emit(apiInterface.getNewsFromQuery(q, sort,APIKEY))
    }.flowOn(Dispatchers.IO)



}

