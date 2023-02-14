package com.example.bankingapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.R
import com.example.bankingapp.presentation.customer.CustomerViewModel
import com.example.bankingapp.presentation.navigation.MainNavigation
import com.example.bankingapp.presentation.navigation.NavControllerItem
import com.example.bankingapp.ui.theme.BankingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val customerViewModel: CustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    MainNavigation(
                        navController = navController,
                        customerViewModel = customerViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {TopAppBar(title = { Text(text = "Who are you?")})}) {
        Log.d("TAG", "MainScreen: ${it}")
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate(NavControllerItem.CustomerAuthScreen.route) }) {
                Text(text = "Customer")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate(NavControllerItem.AdminScreen.route) }) {
                Text(text = "Admin")
            }
        }

    }

}

