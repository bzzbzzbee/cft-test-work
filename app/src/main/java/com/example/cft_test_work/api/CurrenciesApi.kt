package com.example.cft_test_work.api

import com.example.cft_test_work.data.pojo.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CurrenciesApi {
    @GET
    suspend fun loadResponse(@Url url: String): Response<Root>
}