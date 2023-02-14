package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.repository.CustomerRepository
import kotlinx.coroutines.flow.flow
import java.time.temporal.TemporalAmount
import javax.inject.Inject

class SavingAccountWithdrawalsUseCase @Inject constructor(private val customerRepository: CustomerRepository) {


    operator fun invoke(accountId: String, amount: String) = flow<String> {




    }


}