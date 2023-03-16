package repository

import Model.MyDBNews
import androidx.lifecycle.LiveData
import dao.NewsDao
import javax.inject.Inject

class DBRepository @Inject constructor(val newsDao: NewsDao){

    val readallnews: LiveData<List<MyDBNews>> = newsDao.readAllNews()

    suspend fun addNews(myDBNews: MyDBNews){
        newsDao.addNews(myDBNews)
    }
    suspend fun deleteItemNews(myDBNews: MyDBNews){
        newsDao.deleteItemNews(myDBNews)
    }

    suspend fun deleteAll(){
        newsDao.deleteAll()
    }
}