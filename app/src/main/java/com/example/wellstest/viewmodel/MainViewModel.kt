package com.example.wellstest.viewmodel

import androidx.lifecycle.*
import com.example.wellstest.data.Repository
import com.example.wellstest.model.NewsResponse
import com.example.wellstest.model.State
import kotlinx.coroutines.launch
import java.io.IOException


class MainViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    private val _apiStateEvent = MutableLiveData<State>()
    val apiStateEvent: LiveData<State>
        get() = _apiStateEvent

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onLifeCycleStart() {
        getNews()
    }

    fun getNews() {
        _apiStateEvent.value = State.Loading(true)
        viewModelScope.launch {
            try {
                val newsResponse = repository.getNews("US")
                _newsResponse.value = newsResponse
                _apiStateEvent.value = State.Success
            } catch (exception: IOException) {
                _apiStateEvent.value = exception.message?.let { State.Error(it) }
            }
            _apiStateEvent.value = State.Loading(false)
        }
    }
}