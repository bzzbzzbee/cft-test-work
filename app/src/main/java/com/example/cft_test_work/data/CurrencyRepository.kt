package com.example.cft_test_work.data

import android.util.Log
import com.example.cft_test_work.api.CurrenciesApi
import com.example.cft_test_work.data.entities.Currency
import java.io.IOException
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val currenciesApi: CurrenciesApi
) {
    suspend fun initCurrencies() {
        try {
            val response = currenciesApi.loadResponse("daily_json.js")

            if (response.isSuccessful) {
                val currencies = response.body()?.currencies

                currencies?.forEach { (_, v) ->
                    val currency = Currency(v.charCode, v.value, v.iD, v.nominal, v.numCode, v.name)
                    currencyDao.insert(currency)
                }
            } else throw IOException(response.errorBody().toString())
        } catch (t: Throwable) {
            Log.e("Db populating ex: ", t.message.toString())
        }
    }

    suspend fun updateCurrencies() {
        try {
            val response = currenciesApi.loadResponse("daily_json.js")

            if (response.isSuccessful) {
                val currencies = response.body()?.currencies

                currencies?.forEach { (_, v) ->
                    val currency = Currency(v.charCode, v.value, v.iD, v.nominal, v.numCode, v.name)
                    currencyDao.updateCurrency(currency)
                }
            } else throw IOException(response.errorBody().toString())
        } catch (t: Throwable) {
            Log.e("Update ex: ", t.message.toString())
        }
    }

    fun getCurrencies() = currencyDao.getAllCurrencies()
    fun getCurrencyByCharCode(charCode: String) = currencyDao.getCurrencyByCharCode(charCode)
}