package com.rrat.doggydex.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.auth.AuthNavDestinations.LoginScreenDestination
import com.rrat.doggydex.auth.AuthNavDestinations.SignUpScreenDestination
import com.rrat.doggydex.composables.ErrorDialog
import com.rrat.doggydex.composables.LoadingWheel
import com.rrat.doggydex.core.model.User

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAutoLogin: (User) -> Unit
){
    val user = viewModel.user.value
    if (user != null) {
        onAutoLogin(user)
    }
    val navController = rememberNavController()
    AuthNavHost(
        navController = navController,
        onLoginButtonClick = {email, pass -> (viewModel.signIn(email, pass))},
        onSignUpButtonClick = { email, pass, passConfirm ->
            (viewModel.signUp(
                email,
                pass,
                passConfirm
            ))
        }
    )

    val status = viewModel.status.value

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(status.message, onDialogDismiss = {viewModel.resetApiResponseStatus()})
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String)->Unit,
    onSignUpButtonClick: (email: String, pass: String, passConfirm:String) -> Unit
    ) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {

        composable(route = LoginScreenDestination) {
            LoginScreen(
                onLoginButtonClick = onLoginButtonClick,
                onRegisterButtonClick = {
                    navController.navigate(route = SignUpScreenDestination)
                }
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onNavigationIconClick = {
                    navController.navigateUp()
                },
                onSignUpButtonClick = onSignUpButtonClick
            )
        }
    }
}
