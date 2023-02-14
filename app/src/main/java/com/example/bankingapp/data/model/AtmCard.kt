package com.example.bankingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AtmCard(
    @PrimaryKey(autoGenerate = false)
    val atmCardNumber: String,
    val accountNumber:String,
    val cvv: String,
    val expiryDate: String,
    val atmHolderName: String
)
