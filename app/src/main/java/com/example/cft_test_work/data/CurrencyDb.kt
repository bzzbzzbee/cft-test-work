package com.example.cft_test_work.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cft_test_work.data.entities.Currency

@Database(
    entities = [Currency::class],
    version = 1,
    exportSchema = false
)

abstract class CurrencyDb : RoomDatabase() {
    abstract fun currenciesDao(): CurrencyDao
}