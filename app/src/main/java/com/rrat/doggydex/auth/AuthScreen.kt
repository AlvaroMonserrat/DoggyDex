package com.rrat.doggydex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rrat.doggydex.auth.AuthNavDestinations.LoginScreenDestination
import com.rrat.doggydex.auth.AuthNavDestinations.SignUpScreenDestination

@Composable
fun AuthScreen(){
    val navController = rememberNavController()
    AuthNavHost(navController = navController)
}

@Composable
private fun AuthNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {

        composable(route = LoginScreenDestination) {
            LoginScreen(
                onRegisterButtonClick = {
                    navController.navigate(route = SignUpScreenDestination)
                }
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onNavigationIconClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}