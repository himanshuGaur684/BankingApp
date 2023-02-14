package com.example.bankingapp.presentation.customer

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bankingapp.domain.use_cases.Authentication
import com.example.bankingapp.presentation.navigation.NavControllerItem

@Composable
fun CustomerAuthentication(navController: NavController, viewModel: CustomerViewModel) {

    val customerIdOrAccountNumber = rememberSaveable {
        mutableStateOf("")
    }
    val auth = viewModel.auth.value

    if (auth.second != Authentication.IDLE) {
        navController.navigate("customer_screen/${customerIdOrAccountNumber.value}")
        viewModel.resetAuth()
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "Customer Authentication") })
    }) {
        Log.d("TAG", "CustomerAuthentication: ${it}")

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(value = customerIdOrAccountNumber.value, onValueChange = {
                customerIdOrAccountNumber.value = it
            },
                placeholder = { Text(text = "Customer ID or Account Number") })
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                viewModel.authenticateCustomer(customerIdOrAccountNumber.value)
            }) {
                Text(text = "Login")
            }
        }
    }

}