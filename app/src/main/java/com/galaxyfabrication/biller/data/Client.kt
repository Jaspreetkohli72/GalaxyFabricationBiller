package com.galaxyfabrication.biller.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true) val srno: Int = 0,
    val name: String,
    val date: String
)
