package com.example.bankingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.LoanType
import com.example.bankingapp.utils.TransactionPurpose
import com.example.bankingapp.utils.TransactionType

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val accountId: String,
    val amount: String,
    val timeStamp: String,
    val accountType: AccountType,
    val loanType: LoanType = LoanType.NOTHING,
    val purpose: TransactionPurpose,
    val transactionType: TransactionType = TransactionType.NOTHING
)
