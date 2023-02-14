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

@Composable
fun CreateCustomerScreen(navController: NavController,viewModel:CustomerViewModel) {

    val name = rememberSaveable { mutableStateOf("") }
    val age = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val address = rememberSaveable { mutableStateOf("") }
    val phoneNumber = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val errorChannel = viewModel.errorChannel.value
    if (errorChannel.isNotEmpty()) {
        Toast.makeText(context, errorChannel, Toast.LENGTH_LONG).show()
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Create Customer") })
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

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = age.value,
                onValueChange = { age.value = it },
                placeholder = { Text(text = "Age") })
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text(text = "Email") })
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = address.value,
                onValueChange = { address.value = it },
                placeholder = { Text(text = "Address") })
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                placeholder = { Text(text = "Phone Number") })

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                viewModel.createCustomer(
                    name = name.value,
                    email = email.value,
                    age = age.value,
                    address = address.value,
                    phoneNumber = phoneNumber.value,
                )
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Submit")
            }
        }
    }


}