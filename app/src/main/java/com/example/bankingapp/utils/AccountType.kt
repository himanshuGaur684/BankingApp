package com.example.bankingapp.utils

enum class AccountType(val accountTypeName: String) {
    SAVING_AC("Saving Account"),
    CURRENT_AC("Current Account"),
    LOAN_AC("Loan Account")
}


enum class LoanType(val interest: Float, val loanName: String) {
    HOME_LOAN(7f, "Home Loan"),
    CAR_LOAN(8f, "Car Loan"),
    PERSONAL_LOAN(12f, "Personal Loan"),
    BUSINESS_LOAN(15f, "Business Loan"),
    NOTHING(0f, "Nothing")
}

enum class TransactionPurpose {
    DEPOSIT,
    WITHDRAWALS,
    LOAN_REPAYMENT
}

enum class TransactionType {
    ATM, DIRECT,NOTHING
}

enum class WithdrawType {
    ATM,
    DIRECT
}

fun String.isNumber(): Boolean = try {
    this.toDouble()
    true
} catch (e: java.lang.Exception) {
    false
}