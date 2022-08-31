package com.rrat.doggydex.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rrat.doggydex.R
import com.rrat.doggydex.composables.AuthEmailField
import com.rrat.doggydex.composables.AuthFieldPassword
import com.rrat.doggydex.composables.BackNavigationIcon

@Composable
fun SignUpScreen(
    onNavigationIconClick: ()->Unit,
    onSignUpButtonClick: (email: String, pass: String, passConfirm:String) -> Unit
) {
    Scaffold(
        topBar = { SignUpTopBar(onClick = onNavigationIconClick) }
    ) {
        SignUpSection(
            modifier = Modifier.padding(it),
            onSignUpButtonClick = onSignUpButtonClick
        )
    }
}

@Composable
fun SignUpSection(
    modifier: Modifier = Modifier,
    onSignUpButtonClick: (email: String, pass: String, passConfirm:String) -> Unit
){

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var passwordConfirm by rememberSaveable { mutableStateOf("") }
    var passwordConfirmHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthEmailField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.email),
            email = email,
            onTextChanged = { email = it }
        )

        AuthFieldPassword(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = stringResource(id = R.string.password),
            password = password,
            passwordHidden = passwordHidden,
            onChangePasswordHidden = {passwordHidden = it},
            onTextChanged = { password = it }
        )

        AuthFieldPassword(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = stringResource(id = R.string.confirm_password),
            password = passwordConfirm,
            passwordHidden = passwordConfirmHidden,
            onChangePasswordHidden = {passwordConfirmHidden = it},
            onTextChanged = { passwordConfirm = it }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics { testTag = "sign-up-button" },
            onClick = {onSignUpButtonClick(email, password, passwordConfirm)}
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SignUpTopBar(onClick: ()->Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = { BackNavigationIcon(onClick = onClick) }
    )
}