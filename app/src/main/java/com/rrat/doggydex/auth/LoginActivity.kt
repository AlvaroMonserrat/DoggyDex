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

class LoginActivity : ComponentActivity(),
    LoginFragment.LoginFragmentActions,
        SignUpFragment.SignUpFragmentActions
{

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent { 
            DoggyDexTheme() {
                AuthScreen()
            }
        }
        
       /* binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this){
                status->
                renderResponse(status)
        }

        viewModel.user.observe(this){
            user ->
            if(user != null){
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }*/
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun renderResponse(status: ApiResponseStatus<User>?)=with(binding) {
        when (status) {
            is ApiResponseStatus.Error -> {
                progressBar.visibility = View.GONE
                showErrorDialog(status.message)
            }
            is ApiResponseStatus.Loading -> progressBar.visibility = View.VISIBLE
            is ApiResponseStatus.Success -> progressBar.visibility = View.GONE
            else -> showErrorDialog("Error Unknown")
        }
    }

    private fun showErrorDialog(message: String){
        AlertDialog.Builder(this)
            .setTitle(R.string.there_was_an_error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) {
                _, _ ->
            }
            .create()
            .show()

    }


    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onSignInFieldsValidated(email: String, password: String) {
        viewModel.signIn(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.signUp(email, password, passwordConfirmation)
    }
}