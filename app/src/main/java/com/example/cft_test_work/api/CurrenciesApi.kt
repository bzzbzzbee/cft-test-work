package com.example.cft_test_work.api

import com.example.cft_test_work.data.pojo.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrenciesApi {
    @GET("{resource}")
    suspend fun loadResponse(@Path("resource") resource: String): Response<Root>
}