package com.example.bankingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.LoanType

@Entity
data class Account(
    @PrimaryKey(autoGenerate = false)
    val accountId: String,
    val customerId: String,
    val accountType: AccountType,
    val minimumAmount: String,
    val noOfTransactionInCurrentMonth: Int = 0,
    val loanType: LoanType
)
