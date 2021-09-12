package com.example.cft_test_work.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cft_test_work.CURRENCY_TABLE_NAME

@Entity(
    tableName = CURRENCY_TABLE_NAME,
    indices = [Index(value = ["name", "id"], unique = true)]
)
data class Currency(
    val charCode: String,
    val value: Double,
    val id: String,
    val nominal: Int,
    val numCode: String,
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var cId: Int = 0
}