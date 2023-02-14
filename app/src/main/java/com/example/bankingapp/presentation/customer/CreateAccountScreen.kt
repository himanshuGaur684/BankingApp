package com.example.bankingapp.presentation.customer

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bankingapp.utils.AccountType
import com.example.bankingapp.utils.LoanType

@Composable
fun CreateAccountScreen(navController: NavController, viewModel: CustomerViewModel,customerId:String) {

    val name = rememberSaveable { mutableStateOf("") }
    val amount = rememberSaveable { mutableStateOf("") }
    val accountType = rememberSaveable { mutableStateOf(AccountType.SAVING_AC) }
    val loanType = rememberSaveable { mutableStateOf(LoanType.HOME_LOAN) }

    val loanDuration = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val errorChannel = viewModel.errorChannel.value
    if (errorChannel.isNotEmpty()) {
        Toast.makeText(context, errorChannel, Toast.LENGTH_LONG).show()
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Create Account") })
    }) {
        Log.d("TAG", "CustomerCreateNewAccountScreen: ${it}")
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name.value,
                onValueChange = { name.value = it },
                placeholder = { Text(text = "Name") })
            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = amount.value,
                onValueChange = { amount.value = it },
                placeholder = { Text(text = "Amount") })
            Spacer(modifier = Modifier.height(20.dp))

            if (accountType.value == AccountType.LOAN_AC) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = loanDuration.value,
                    onValueChange = { loanDuration.value = it },
                    placeholder = { Text(text = "Loan Duration") })
                Spacer(modifier = Modifier.height(20.dp))
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Type of Account", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = accountType.value == AccountType.SAVING_AC,
                        onClick = {
                            accountType.value = AccountType.SAVING_AC
                        }
                    )
                    Text(text = "Saving A/C")
                }
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = accountType.value == AccountType.CURRENT_AC,
                        onClick = {
                            accountType.value = AccountType.CURRENT_AC
                        }
                    )
                    Text(text = "Current A/C")
                }

                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = accountType.value == AccountType.LOAN_AC,
                        onClick = {
                            accountType.value = AccountType.LOAN_AC
                        }
                    )
                    Text(text = "Loan A/C")
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            if (accountType.value == AccountType.LOAN_AC) {
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
                            selected = loanType.value == LoanType.HOME_LOAN,
                            onClick = {
                                loanType.value = LoanType.HOME_LOAN
                            }
                        )
                        Text(text = "Home Loan")
                    }
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = loanType.value == LoanType.CAR_LOAN,
                            onClick = {
                                loanType.value = LoanType.CAR_LOAN
                            }
                        )
                        Text(text = "Car Loan")
                    }

                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = loanType.value == LoanType.BUSINESS_LOAN,
                            onClick = {
                                loanType.value = LoanType.BUSINESS_LOAN
                            }
                        )
                        Text(text = "Business Loan")
                    }
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = loanType.value == LoanType.PERSONAL_LOAN,
                            onClick = {
                                loanType.value = LoanType.PERSONAL_LOAN
                            }
                        )
                        Text(text = "Personal Loan")
                    }

                }
            }

            Button(onClick = {
                viewModel.createNewAccount(
                    name = name.value,
                    accountType = accountType.value,
                    minimumAmount = amount.value,
                    loanType = loanType.value,
                    customerId = customerId
                )

            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Submit")
            }
        }
    }


}