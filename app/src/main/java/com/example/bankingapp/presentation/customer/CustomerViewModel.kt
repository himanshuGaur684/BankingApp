package com.example.bankingapp.presentation.customer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.model.Account
import com.example.bankingapp.data.model.Customer
import com.example.bankingapp.data.model.Transaction
import com.example.bankingapp.domain.use_cases.*
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.LoanType
import com.example.bankingapp.utils.WithdrawType
import com.example.bankingapp.utils.isNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val createCustomerAccountUseCase: CreateAccountUseCase,
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val getCustomerListUseCase: GetCustomerListUseCase,
    private val customerAuthenticateUseCase: AuthenticateCustomerUseCase,
    private val getAllAccountsUseCase: GetAccountsUseCase,
    private val depositTransactionUseCase: DepositTransactionUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val withdrawTransactionUseCase: WithdrawTransactionUseCase
) :
    ViewModel() {


    private val _errorChannel: MutableState<String> = mutableStateOf("")
    val errorChannel: State<String> get() = _errorChannel

    private val _customerList: MutableState<List<Customer>> = mutableStateOf(emptyList())
    val customerList: State<List<Customer>> get() = _customerList

    private val _auth: MutableState<Pair<Boolean, Authentication>> =
        mutableStateOf(Pair(false, Authentication.IDLE))
    val auth: State<Pair<Boolean, Authentication>> get() = _auth
    fun resetAuth() {
        _auth.value = Pair(false, Authentication.IDLE)
    }

    private val _accountList: MutableState<List<Account>> = mutableStateOf(emptyList())
    val accountList: State<List<Account>> get() = _accountList

    private val _transactionList: MutableState<List<Transaction>> = mutableStateOf(emptyList())
    val transactionList: State<List<Transaction>> get() = _transactionList


    init {
        getCustomerList()
    }


    fun authenticateCustomer(accountNoOrCustomerId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _auth.value = customerAuthenticateUseCase.invoke(accountNoOrCustomerId)
        }

    private fun getCustomerList() = viewModelScope.launch {
        getCustomerListUseCase.invoke().onEach {
            _customerList.value = it
        }.launchIn(viewModelScope)
    }

    fun createCustomer(
        name: String,
        email: String,
        age: String, address: String, phoneNumber: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        when {
            name.isEmpty() -> {
                _errorChannel.value = "Name is Empty"
            }
            email.isEmpty() -> {
                _errorChannel.value = "Email is Empty"
            }
            age.isEmpty() || !age.isNumber() -> {
                _errorChannel.value = "Age is Empty"
            }
            address.isEmpty() -> {
                _errorChannel.value = "Address is not empty"
            }
            phoneNumber.isEmpty() -> {
                _errorChannel.value = "Phone Number cant be empty"
            }
            else -> {
                createCustomerUseCase.invoke(
                    name = name,
                    address = address,
                    email = email,
                    phoneNumber = phoneNumber,
                    age = age
                ).launchIn(viewModelScope)
            }
        }


    }

    fun createNewAccount(
        name: String,
        accountType: AccountType,
        minimumAmount: String,
        loanType: LoanType,
        customerId: String
    ) =
        viewModelScope.launch {
            when {
                name.isEmpty() -> {
                    _errorChannel.value = "Name is Empty"
                }
                minimumAmount.isEmpty() || !minimumAmount.isNumber() || !minimumAmountValidate(
                    accountType,
                    minimumAmount.toDouble()
                ).first -> {
                    _errorChannel.value = minimumAmountValidate(
                        accountType,
                        minimumAmount.toDouble()
                    ).second
                }
                else -> {
                    createCustomerAccountUseCase.invoke(
                        name = name,
                        accountType = accountType,
                        minimumAmount = minimumAmount,
                        loanType = loanType,
                        customerId = customerId
                    ).onEach {
                        Log.d("TAG", "createNewAccount: ${it}")
                        _errorChannel.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }

    fun getAccountList(customerId: String) = viewModelScope.launch {
        getAllAccountsUseCase.invoke(customerId).onEach {
            _accountList.value = it
        }.launchIn(viewModelScope)
    }

    fun deposit(accountId: String, amount: String) =
        viewModelScope.launch(Dispatchers.IO) {
            if (amount.isNumber()) {
                depositTransactionUseCase.invoke(accountId, amount).onEach {
                    _errorChannel.value = "Deposit Successful"
                }.launchIn(viewModelScope)
            } else {
                _errorChannel.value = "Please enter valid amount"
            }
        }

    fun getTransactions(accountId: String) = viewModelScope.launch {
        getTransactionListUseCase.invoke(accountId).onEach {
            _transactionList.value = it
        }.launchIn(viewModelScope)
    }

    fun withdrawAmount(accountId: String, amount: String, withDrawType: WithdrawType) =
        viewModelScope.launch(Dispatchers.IO) {
            withdrawTransactionUseCase.invoke(
                accountId = accountId,
                amount = amount,
                withdrawType = withDrawType
            ).onEach {
                Log.d("TAG", "withdrawAmount: $it")
            }.launchIn(viewModelScope)
        }

    private fun minimumAmountValidate(
        accountType: AccountType,
        amount: Double
    ): Pair<Boolean, String> {
        return when (accountType) {
            AccountType.LOAN_AC -> {
                Pair(amount >= 500000, "Minimum amount amount to get loan is 5,000,00")
            }
            AccountType.SAVING_AC -> {
                Pair(amount >= 10000, "Minimum amount to open saving a/c is 10,000")
            }
            AccountType.CURRENT_AC -> {
                Pair(amount >= 100000, "Minimum amount to open current a/c is 1,000,00")
            }
        }
    }


}