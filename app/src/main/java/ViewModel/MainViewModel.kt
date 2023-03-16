package ViewModel

import Model.Article
import Model.NewsModel
import repository.MainRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import utils.Events
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {



    val allListof: MutableLiveData<Events<NewsModel>> = MutableLiveData()

    fun get(q: String,sort:String){
        viewModelScope.launch {
            allListof.postValue(Events.Loading())
            mainRepository.getModel(q,sort).catch {
                Log.e("MainViewModel", "I get : ${it.localizedMessage}")
                allListof.postValue(Events.Error(msg = it.localizedMessage))
            }.collect{news->
                allListof.postValue(Events.Success(news))
            }
        }
    }




}


