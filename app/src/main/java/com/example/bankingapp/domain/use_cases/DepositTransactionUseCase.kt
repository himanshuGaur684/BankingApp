package com.example.bankingapp.domain.use_cases

import androidx.compose.ui.unit.Constraints
import com.example.bankingapp.data.model.Account
import com.example.bankingapp.data.model.Transaction
import com.example.bankingapp.data.repository.CustomerRepository
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.Constant
import com.example.bankingapp.utils.TransactionPurpose
import com.example.bankingapp.utils.getCurrentDate
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class DepositTransactionUseCase @Inject constructor(private val customerRepository: CustomerRepository) {
    operator fun invoke(accountId: String, amount: String) = flow<String> {
        customerRepository.insertTransaction(
            Transaction(
                id= UUID.randomUUID().toString(),
                accountId = accountId,
                amount=amount,
                timeStamp = getCurrentDate(Constant.DATE_FORMAT_SERVER),
                accountType = AccountType.SAVING_AC,
                purpose = TransactionPurpose.DEPOSIT
            )
        )
        val account = customerRepository.getAccount(accountId = accountId)
        val netAmount = account.minimumAmount.toDouble()+amount.toDouble()
        customerRepository.updateAccount(
            accountId = accountId,
            amount = netAmount.toString()
        )
        emit("Deposit Successful")
    }.catch { emit("Error Occredd") }

}