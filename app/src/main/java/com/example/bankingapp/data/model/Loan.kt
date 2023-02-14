package com.example.bankingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bankingapp.utils.LoanType

@Entity
data class Loan(
    val customerId: String,
    @PrimaryKey(autoGenerate = false)
    val accountNumber: String,
    val duration: String,
    val amount: String,
    val loanType: LoanType
)
