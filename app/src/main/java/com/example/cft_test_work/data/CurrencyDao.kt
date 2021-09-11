package com.example.cft_test_work.data

import androidx.room.*
import com.example.cft_test_work.data.entities.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currency: Currency): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currencies: List<Currency>)

    @Query("SELECT * FROM currencies WHERE id IN (:currencyId)")
    fun getCurrencyById(currencyId: String): Currency

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): Flow<List<Currency>>

    @Update
    suspend fun updateCurrency(currency: Currency)

    @Query("SELECT COUNT(*) FROM currencies")
    fun getCurrenciesCount(): Long

    @Update
    suspend fun updateAllCurrencies(currencies: List<Currency>)
}