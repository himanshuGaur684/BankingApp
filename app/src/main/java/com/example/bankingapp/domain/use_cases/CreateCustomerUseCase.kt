package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.model.Customer
import com.example.bankingapp.data.repository.CustomerRepository
import com.example.bankingapp.utils.generateRandomNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateCustomerUseCase @Inject constructor(private val customerRepository: CustomerRepository) {
    operator fun invoke(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        age: String
    ) = flow<String> {
        val customer = Customer(
            name = name,
            address = address,
            email = email,
            phoneNumber = phoneNumber,
            age = age,
            id = generateRandomNumber(10)
        )
        customerRepository.createCustomer(customer = customer)
        emit("Customer Created Successfully")
    }.catch {
        emit("Error Occurred")
    }.flowOn(Dispatchers.IO)

}