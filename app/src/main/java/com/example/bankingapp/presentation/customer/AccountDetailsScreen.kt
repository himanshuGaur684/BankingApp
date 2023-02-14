package com.example.bankingapp.presentation.customer

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bankingapp.data.model.Account
import com.example.bankingapp.utils.AccountType

@Composable
fun CustomerScreen(navController: NavController, viewModel: CustomerViewModel, id: String) {

    val accountList = viewModel.accountList.value

    LaunchedEffect(key1 = id, block = {
        viewModel.getAccountList(id)
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Account Details") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("create_account/${id}")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }) {
        Log.d("TAG", "CustomerScreen: ${it}")

        LazyColumn {
            items(accountList) {
                AccountListItem(account = it) {
                    navController.navigate("account_operation_screen/${it.accountId}")
                }
            }
        }
    }
}


@Composable
fun AccountListItem(account: Account, onClick: (Account) -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick.invoke(account) }) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Account-Id: " + account.accountId, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Amount: " + account.minimumAmount, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Account Type: ${account.accountType.accountTypeName}",
                style = MaterialTheme.typography.body1
            )
            if (account.accountType == AccountType.LOAN_AC) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Loan Type: ${account.loanType.loanName}",
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}