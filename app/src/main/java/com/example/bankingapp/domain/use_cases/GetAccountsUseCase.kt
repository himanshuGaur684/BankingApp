package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.model.Account
import com.example.bankingapp.data.repository.CustomerRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(private val customerRepository: CustomerRepository) {

    operator fun invoke(customerID:String) = flow<List<Account>> {
        emit(customerRepository.getAccountList(customerID))
    }.catch {
        emit(emptyList())
    }

}