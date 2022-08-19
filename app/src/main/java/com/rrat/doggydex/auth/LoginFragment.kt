package com.rrat.doggydex.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rrat.doggydex.R
import com.rrat.doggydex.Utils.Companion.isValidEmail
import com.rrat.doggydex.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    interface LoginFragmentActions{
        fun onRegisterButtonClick()
        fun onSignInFieldsValidated(email: String, password: String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions

    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement LoginFragmentAction")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }

        binding.loginButton.setOnClickListener {
            validateFields()
        }

        return binding.root
    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""


        val email = binding.emailEdit.text.toString()

        if (!isValidEmail(email)){
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if(password.isEmpty()){
            binding.passwordInput.error = getString(R.string.password_is_not_valid)
            return
        }

        loginFragmentActions.onSignInFieldsValidated(email, password)
    }

}