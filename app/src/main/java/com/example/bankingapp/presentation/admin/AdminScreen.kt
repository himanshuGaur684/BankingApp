package com.example.bankingapp.presentation.admin

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bankingapp.presentation.customer.CustomerViewModel
import com.example.bankingapp.presentation.navigation.NavControllerItem

@Composable
fun AdminScreen(navController: NavController, viewModel: CustomerViewModel) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Admin Operations")
            })
        }) {
        Log.d("TAG", "AdminScreen: ${it}")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = { navController.navigate(NavControllerItem.CustomerListScreen.route) }) {
                Text(text = "Customer Details")
            }

        }


    }


}