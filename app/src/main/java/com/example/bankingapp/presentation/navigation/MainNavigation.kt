package com.example.bankingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bankingapp.presentation.MainScreen
import com.example.bankingapp.presentation.admin.AdminScreen
import com.example.bankingapp.presentation.customer.*


@Composable
fun MainNavigation(navController: NavHostController, customerViewModel: CustomerViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavControllerItem.MainScreenRoute.route
    ) {
        composable(NavControllerItem.MainScreenRoute.route) {
            MainScreen(navController = navController)
        }
        composable(NavControllerItem.AccountOperationScreen.route) {
            val accountId = it.arguments?.getString("account_id").toString()
            AccountOperationScreen(customerViewModel= customerViewModel,navController= navController,accountId)
        }
        composable(NavControllerItem.CreateAccountRoute.route) {
            val customerId = it.arguments?.getString("id").toString()
            CreateAccountScreen(navController, customerViewModel,customerId)
        }
        composable(NavControllerItem.CustomerListScreen.route) {
            CustomerListScreen(navController = navController, viewModel = customerViewModel)
        }
        composable(NavControllerItem.CreateCustomer.route) {
            CreateCustomerScreen(navController = navController, viewModel = customerViewModel)
        }
        composable(NavControllerItem.CustomerScreen.route) {
            val id = it.arguments?.getString("id").toString()
            CustomerScreen(navController=navController, viewModel = customerViewModel, id = id)
        }
        composable(NavControllerItem.AdminScreen.route) {
            AdminScreen(viewModel = customerViewModel, navController = navController)
        }
        composable(NavControllerItem.CustomerAuthScreen.route){
            CustomerAuthentication(navController = navController, viewModel = customerViewModel)
        }
    }
}
