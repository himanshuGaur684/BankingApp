package com.example.bankingapp.presentation.navigation

sealed class NavControllerItem(val route: String) {

    object MainScreenRoute : NavControllerItem("main_screen")
    object CreateAccountRoute : NavControllerItem("create_account/{id}")
    object AccountOperationScreen : NavControllerItem("account_operation_screen/{account_id}")

    object CustomerListScreen : NavControllerItem("customer_list")
    object CreateCustomer : NavControllerItem("create_customer")
    object CustomerScreen : NavControllerItem("customer_screen/{id}")
    object CustomerOperationScreen : NavControllerItem("customer_operation")
    object CustomerLoginScreen : NavControllerItem("customer_login")
    object CustomerAuthScreen : NavControllerItem("customer_auth")

    object AdminScreen : NavControllerItem("admin_screen")

}


