package com.example.bankingapp.presentation.customer

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bankingapp.data.model.Transaction
import com.example.bankingapp.utils.LoanType
import com.example.bankingapp.utils.WithdrawType


enum class CustomerOperation {
    DEPOSIT,
    LOAN_REPAYMENT,
    WITHDRAWALS,
    ACCOUNT_DETAILS
}

@Composable
fun AccountOperationScreen(
    customerViewModel: CustomerViewModel,
    navController: NavController,
    accountId: String
) {

    val operationType = rememberSaveable {
        mutableStateOf(CustomerOperation.DEPOSIT)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Customer Operations") }) }) {
        Log.d("TAG", "CustomerOperationScreen: ${it}")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Button(onClick = {
                    operationType.value = CustomerOperation.DEPOSIT
                }) {
                    Text(text = "Deposit")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    operationType.value = CustomerOperation.WITHDRAWALS
                }) {
                    Text(text = "Withdrawals")
                }
            }
            Row() {
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    operationType.value = CustomerOperation.LOAN_REPAYMENT
                }) {
                    Text(text = "Loan Repayments")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    operationType.value = CustomerOperation.ACCOUNT_DETAILS
                }) {
                    Text(text = "Account Details")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            when (operationType.value) {
                CustomerOperation.DEPOSIT -> {
                    DepositScreen(
                        accountId = accountId,
                        customerViewModel = customerViewModel,
                        navController = navController
                    )
                }
                CustomerOperation.ACCOUNT_DETAILS -> {
                    AccountDetails(accountId = accountId, viewModel = customerViewModel)
                }
                CustomerOperation.WITHDRAWALS -> {
                    WithdrawalAmount(accountId = accountId, customerViewModel = customerViewModel)
                }
                CustomerOperation.LOAN_REPAYMENT -> {

                }
            }
        }
    }
}


@Composable
fun DepositScreen(
    accountId: String,
    customerViewModel: CustomerViewModel,
    navController: NavController
) {
    val amount = rememberSaveable { mutableStateOf("") }
    val message = customerViewModel.errorChannel.value
    val context = LocalContext.current

    if (message.isNotEmpty()) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Deposit", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount.value, onValueChange = {
                amount.value = it
            }, placeholder = { Text(text = "Deposit Amount") })
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            customerViewModel.deposit(accountId, amount.value)
        }) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun AccountDetails(accountId: String, viewModel: CustomerViewModel) {
    val transactionList = viewModel.transactionList.value
    LaunchedEffect(key1 = accountId, block = {
        viewModel.getTransactions(accountId)
    })
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Account Details", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(12.dp))
        transactionList.forEach {
            AccountDetailsListItem(transaction = it)
        }
    }

}

@Composable
fun AccountDetailsListItem(transaction: Transaction) {
    Surface(Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Text(text = "Amount: " + transaction.amount, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Time: " + transaction.timeStamp, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Account Type: " + transaction.accountType.accountTypeName,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Purpose: " + transaction.purpose, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))
        }

    }

}

@Composable
fun WithdrawalAmount(accountId: String, customerViewModel: CustomerViewModel) {
    val amount = rememberSaveable { mutableStateOf("") }

    val withdrawType = rememberSaveable { mutableStateOf(WithdrawType.ATM) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Withdraw", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount.value, onValueChange = {
                amount.value = it
            }, placeholder = { Text(text = "Withdraw Amount") })
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Type of Loan", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = withdrawType.value == WithdrawType.ATM,
                    onClick = {
                        withdrawType.value = WithdrawType.ATM
                    }
                )
                Text(text = "ATM")
            }
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = withdrawType.value == WithdrawType.DIRECT,
                    onClick = {
                        withdrawType.value = WithdrawType.DIRECT
                    }
                )
                Text(text = "Direct")
            }
        }

        Button(onClick = {
            customerViewModel.withdrawAmount(
                accountId,
                amount = amount.value,
                withDrawType = withdrawType.value
            )
        }) {
            Text(text = "Submit")
        }
    }
}






