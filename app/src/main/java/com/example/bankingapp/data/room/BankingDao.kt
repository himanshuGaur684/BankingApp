package com.example.bankingapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bankingapp.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BankingDao {

    @Insert()
    suspend fun insertAccount(account: Account)

    @Insert()
    suspend fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM CUSTOMER")
    suspend fun getAllCustomer(): List<Customer>

    @Insert
    suspend fun insertAtmCard(atmCard: AtmCard)

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM Account WHERE customerId==:customerId")
    suspend fun getAccountList(customerId: String): List<Account>

    @Insert
    suspend fun insertLoan(loan: Loan)

    @Query("SELECT * FROM Customer")
    fun getCustomerList(): Flow<List<Customer>>

    @Query("SELECT EXISTS(SELECT * FROM Customer WHERE id = :str)")
    suspend fun authenticateCustomer(str: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM Account WHERE accountId = :str)")
    suspend fun authenticateCustomerWithAccountId(str: String): Boolean

    @Query("SELECT * FROM Customer WHERE id=:customerId")
    suspend fun getCustomer(customerId: String): Customer

    @Query("UPDATE Account SET minimumAmount = :amount WHERE accountId = :accountId")
    suspend fun updateAccount(accountId: String, amount: String)

    @Query("SELECT * FROM `Transaction` WHERE accountId=:accountId")
    suspend fun getTransaction(accountId: String): List<Transaction>

    @Query("SELECT * FROM Account WHERE accountId=:accountId")
    suspend fun getAccount(accountId: String): Account

}