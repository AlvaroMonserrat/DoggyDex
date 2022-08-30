package com.rrat.doggydex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.main.MainActivity
import com.rrat.doggydex.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val user = viewModel.user.value


            if (user != null) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }

            DoggyDexTheme() {
                AuthScreen(
                    onDialogDismiss = { resetApiResponseStatus() },
                    onLoginButtonClick = { email, pass ->
                        (viewModel.signIn(email, pass))
                    },
                    onSignUpButtonClick = { email, pass, passConfirm ->
                        (viewModel.signUp(
                            email,
                            pass,
                            passConfirm
                        ))
                    }
                )

            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}