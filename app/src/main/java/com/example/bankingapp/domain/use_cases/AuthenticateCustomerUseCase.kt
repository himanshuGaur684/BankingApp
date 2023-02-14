package com.example.bankingapp.domain.use_cases

import com.example.bankingapp.data.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

enum class Authentication {
    ACCOUNT,
    CUSTOMER,
    NOT_EXIST,
    IDLE
}

class AuthenticateCustomerUseCase @Inject constructor(private val repository: CustomerRepository) {
    suspend operator fun invoke(str: String) : Pair<Boolean, Authentication>  {
        try {
            val checkCustomerID = repository.checkIfRecordExistForCustomerID(str)
            if (checkCustomerID) {
                return Pair(true, Authentication.CUSTOMER)
            }
            val accountId = repository.checkIfRecordExistForAccountID(str)
            if (accountId) {
                return Pair(true, Authentication.ACCOUNT)
            }
//            if (!checkCustomerID && !accountId) {
//                return Pair(false, Authentication.NOT_EXIST)
//            }
            return Pair(false, Authentication.IDLE)
        } catch (e: java.lang.Exception) {
            return Pair(false, Authentication.IDLE)
        }
    }


}