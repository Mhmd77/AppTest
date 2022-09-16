package com.example.testproject.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.testproject.data.source.Repository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application, private val repository: Repository) :
    AndroidViewModel(application) {

    val transactionList = repository.observeTransactionList().map {
        _isLoading.value = false
        it
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            repository.refreshTransactions(application.baseContext)
        }
    }
}