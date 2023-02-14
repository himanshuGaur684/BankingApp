package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.model.Transaction
import com.example.bankingapp.data.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTransactionListUseCase @Inject constructor(private val customerRepository: CustomerRepository) {

    operator fun invoke(accountId: String) = flow<List<Transaction>> {
        emit(customerRepository.getTransactionList(accountId))
    }.catch { emit(emptyList()) }.flowOn(Dispatchers.IO)

}