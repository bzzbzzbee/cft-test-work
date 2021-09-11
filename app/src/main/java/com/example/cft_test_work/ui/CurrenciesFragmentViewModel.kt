package com.example.cft_test_work.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cft_test_work.data.CurrencyRepository
import com.example.cft_test_work.data.entities.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesFragmentViewModel @Inject constructor(private val currencyRepository: CurrencyRepository) :
    ViewModel() {
    init {
        if (currencyRepository.isDbEmpty()) {
            viewModelScope.launch {
                currencyRepository.initCurrencies()
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            currencyRepository.updateCurrencies()
        }
    }

    fun getCurrencies() = currencyRepository.getCurrencies().asLiveData()
}