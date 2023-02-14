package com.example.bankingapp.data.repository

import com.example.bankingapp.data.model.Account
import com.example.bankingapp.data.model.AtmCard
import com.example.bankingapp.data.model.Customer
import com.example.bankingapp.data.model.Transaction
import com.example.bankingapp.data.room.BankingDao
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val bankingDao: BankingDao) {


    suspend fun insertAccount(account: Account) = bankingDao.insertAccount(account)


    suspend fun insertAtmCard(atmCard: AtmCard) = bankingDao.insertAtmCard(atmCard)

    suspend fun getAccountList(customerId: String) = bankingDao.getAccountList(customerId)

    suspend fun getAccount(accountId:String) = bankingDao.getAccount(accountId)

    suspend fun updateAccount(accountId:String,amount:String) = bankingDao.updateAccount(accountId,amount)

    suspend fun createCustomer(customer: Customer) = bankingDao.insertCustomer(customer = customer)

    fun getCustomerList() = bankingDao.getCustomerList()

    suspend fun checkIfRecordExistForCustomerID(str: String) = bankingDao.authenticateCustomer(str)

    suspend fun checkIfRecordExistForAccountID(str: String) =
        bankingDao.authenticateCustomerWithAccountId(str)

    suspend fun getCustomer(customerId: String): Customer = bankingDao.getCustomer(customerId)

    suspend fun insertTransaction(transaction: Transaction)  = bankingDao.insertTransaction(transaction =transaction )

    suspend fun getTransactionList(accountId: String)  = bankingDao.getTransaction(accountId)

}
