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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DoggyDexTheme() {
                AuthScreen(
                    onAutoLogin = {
                        user -> startMainActivity(user)
                    }
                )

            }
        }
    }

    private fun startMainActivity(user: User) {
        User.setLoggedInUser(this, user)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}