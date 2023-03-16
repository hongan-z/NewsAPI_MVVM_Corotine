package ViewModel

import Model.MyDBNews
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import database.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repository.DBRepository

class DBViewModel(application: Application): AndroidViewModel(application) {

    val allnews: LiveData<List<MyDBNews>>
    val repository: DBRepository

    init {
        val newsDao = NewsDatabase.getDatabase(application).newsDao()
        repository = DBRepository(newsDao)
        allnews = repository.readallnews

    }

    fun addNews(myDBNews: MyDBNews){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNews(myDBNews)
        }
    }

    fun deleteItemsNews(myDBNews: MyDBNews){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItemNews(myDBNews)

        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    

}