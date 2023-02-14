package com.example.bankingapp.presentation.customer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bankingapp.data.model.Customer
import com.example.bankingapp.presentation.navigation.NavControllerItem

@Composable
fun CustomerListScreen(navController: NavController, viewModel: CustomerViewModel) {

    val customerList = viewModel.customerList.value

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "Customer List") }
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(NavControllerItem.CreateCustomer.route)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        Log.d("TAG", "CustomerListScreen: ${it}")

        LazyColumn {
            items(customerList) {
                CustomerListItem(it)
            }
        }
    }
}

@Composable
fun CustomerListItem(customer: Customer) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            Text(
                text = customer.id.toString(),
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = customer.name.toString(),
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}