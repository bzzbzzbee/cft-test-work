package com.example.cft_test_work.data.pojo

import com.google.gson.annotations.SerializedName

data class Currency(
    @field:SerializedName("CharCode")
    val charCode: String,
    @field:SerializedName("Value")
    val value: Double,
    @field:SerializedName("Previous")
    val previous: Double,
    @field:SerializedName("ID")
    val iD: String,
    @field:SerializedName("Nominal")
    val nominal: Int,
    @field:SerializedName("NumCode")
    val numCode: String,
    @field:SerializedName("Name")
    val name: String
)