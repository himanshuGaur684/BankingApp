package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.model.Transaction
import com.example.bankingapp.data.repository.CustomerRepository
import com.example.bankingapp.utils.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class WithdrawTransactionUseCase @Inject constructor(private val customerRepository: CustomerRepository) {

    operator fun invoke(accountId: String, amount: String, withdrawType: WithdrawType) =
        flow<String> {

            val account = customerRepository.getAccount(accountId = accountId)
            val transactions = customerRepository.getTransactionList(accountId)

            when (account.accountType) {
                AccountType.LOAN_AC -> {


                }
                AccountType.SAVING_AC -> {
                    if (isUserWithdrawMoreThanFiftyThousandInADay(transactions)) {
                        return@flow
                    } else {
                        when (withdrawType) {
                            WithdrawType.ATM -> {
                                val moreThanFiveTimes =
                                    isUserWithdrawMoreThanFiveTimesInAMonth(transactions)
                                if (!moreThanFiveTimes) {
                                    val accountInfo =
                                        customerRepository.getAccount(accountId = accountId)
                                    if (accountInfo.minimumAmount.toDouble() > amount.toDouble()) {
                                        val netAmount =
                                            accountInfo.minimumAmount.toDouble() - amount.toDouble()
                                        customerRepository.updateAccount(
                                            accountId,
                                            netAmount.toString()
                                        )
                                        customerRepository.insertTransaction(
                                            Transaction(
                                                id = UUID.randomUUID().toString(),
                                                accountId = accountId,
                                                amount = amount,
                                                accountType = AccountType.SAVING_AC,
                                                timeStamp = getCurrentDate(Constant.DATE_FORMAT_SERVER),
                                                loanType = LoanType.NOTHING,
                                                purpose = TransactionPurpose.WITHDRAWALS,
                                                transactionType = TransactionType.ATM
                                            )
                                        )
                                    } else {

                                    }
                                } else {
                                    val accountInfo =
                                        customerRepository.getAccount(accountId = accountId)
                                    if (accountInfo.minimumAmount.toDouble() > amount.toDouble() + 500.0) {
                                        val deductedAmount = amount.toDouble() + 500.0
                                        val netAmount =
                                            accountInfo.minimumAmount.toDouble() - deductedAmount
                                        customerRepository.updateAccount(
                                            accountId,
                                            netAmount.toString()
                                        )
                                        customerRepository.insertTransaction(
                                            Transaction(
                                                id = UUID.randomUUID().toString(),
                                                accountId = accountId,
                                                amount = deductedAmount.toString(),
                                                accountType = AccountType.SAVING_AC,
                                                timeStamp = getCurrentDate(Constant.DATE_FORMAT_SERVER),
                                                loanType = LoanType.NOTHING,
                                                purpose = TransactionPurpose.WITHDRAWALS,
                                                transactionType = TransactionType.ATM
                                            )
                                        )
                                    } else {

                                    }
                                }
                            }
                            WithdrawType.DIRECT -> {
                                val accountInfo =
                                    customerRepository.getAccount(accountId = accountId)
                                if (accountInfo.minimumAmount.toDouble() > amount.toDouble()) {
                                    val netAmount =
                                        accountInfo.minimumAmount.toDouble() - amount.toDouble()
                                    customerRepository.updateAccount(
                                        accountId,
                                        netAmount.toString()
                                    )
                                    customerRepository.insertTransaction(
                                        Transaction(
                                            id = UUID.randomUUID().toString(),
                                            accountId = accountId,
                                            amount = amount,
                                            accountType = AccountType.SAVING_AC,
                                            timeStamp = getCurrentDate(Constant.DATE_FORMAT_SERVER),
                                            loanType = LoanType.NOTHING,
                                            purpose = TransactionPurpose.WITHDRAWALS,
                                            transactionType = TransactionType.DIRECT
                                        )
                                    )
                                } else {

                                }
                            }
                        }
                    }

                }
                AccountType.CURRENT_AC -> {
                    val interestAmount = calculateInterestOnTransactionAmount(amount = amount)
                    val totalAmountForWithdraw = interestAmount + amount.toDouble()
                    if (totalAmountForWithdraw < account.minimumAmount.toDouble()) {
                        val netAmount = account.minimumAmount.toDouble() - totalAmountForWithdraw
                        customerRepository.updateAccount(accountId, netAmount.toString())
                    } else {

                    }

                }
            }

        }

    private fun calculateInterestOnTransactionAmount(amount: String): Double {
        return if (amount.isNumber()) {
            val netAmount = amount.toDouble() * .005
            if (netAmount > 500.0) {
                500.0
            } else {
                netAmount
            }
        } else {
            0.0
        }


    }

    private fun isUserWithdrawMoreThanFiveTimesInAMonth(transactions: List<Transaction>): Boolean {
        val currentDate = getCurrentDate(Constant.DATE_FORMAT_SERVER).split("-")[1]
        return transactions.filter { it.timeStamp.split("-").get(1) == currentDate }.size > 5
    }

    private fun isUserWithdrawMoreThanFiftyThousandInADay(transactions: List<Transaction>): Boolean {
        val currentDate = getCurrentDate(Constant.DATE_FORMAT_SERVER)
        return transactions.filter { it.timeStamp == currentDate }
            .sumOf { it.amount.toDouble() } > 50000
    }

}