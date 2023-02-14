package com.example.bankingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(
    val name: String,
    val address: String,
    val email: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val phoneNumber: String,
    val age: String
)
