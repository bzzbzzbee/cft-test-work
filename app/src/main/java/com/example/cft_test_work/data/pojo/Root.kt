package com.example.cft_test_work.data.pojo

import com.google.gson.annotations.SerializedName

data class Root(

    @field:SerializedName("PreviousURL")
    val previousURL: String,

    @field:SerializedName("Timestamp")
    val timestamp: String,

    @field:SerializedName("Date")
    val date: String,

    @field:SerializedName("PreviousDate")
    val previousDate: String,

    @field:SerializedName("Valute")
    val currencies: Map<String, Currency>
)


