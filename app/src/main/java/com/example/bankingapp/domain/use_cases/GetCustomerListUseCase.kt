package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.model.Customer
import com.example.bankingapp.data.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.Flow
import javax.inject.Inject

class GetCustomerListUseCase @Inject constructor(private val customerRepository: CustomerRepository) {

    operator fun invoke() = customerRepository.getCustomerList()

}