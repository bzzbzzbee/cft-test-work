package com.example.cft_test_work.di

import android.content.Context
import androidx.room.Room
import com.example.cft_test_work.CURRENCY_DB_NAME
import com.example.cft_test_work.data.CurrencyDao
import com.example.cft_test_work.data.CurrencyDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideCurrencyDb(@ApplicationContext context: Context): CurrencyDb {
        return Room.databaseBuilder(
            context.applicationContext,
            CurrencyDb::class.java,
            CURRENCY_DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCurrencyDao(currencyDb: CurrencyDb): CurrencyDao {
        return currencyDb.currenciesDao()
    }
}