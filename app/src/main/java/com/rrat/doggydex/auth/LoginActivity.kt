package com.rrat.doggydex.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.rrat.doggydex.main.MainActivity
import com.rrat.doggydex.R
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.databinding.ActivityLoginBinding
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.model.User

class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val user = viewModel.user.value
            val status = viewModel.status.value

            if (user != null) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }

            DoggyDexTheme() {
                AuthScreen(
                    status = status,
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