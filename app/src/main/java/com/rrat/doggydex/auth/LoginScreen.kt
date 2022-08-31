package com.rrat.doggydex.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
import androidx.compose.ui.unit.sp
import com.rrat.doggydex.R
import com.rrat.doggydex.Utils
import com.rrat.doggydex.composables.AuthEmailField
import com.rrat.doggydex.composables.AuthFieldPassword

@Composable
fun LoginScreen(
    onLoginButtonClick: (String, String)->Unit,
    onRegisterButtonClick: ()->Unit
) {
    Scaffold(
        topBar = { LoginTopBar() }
    ) {
        LoginSection(
            modifier = Modifier.padding(it),
            onLoginButtonClick=onLoginButtonClick,
            onRegisterButtonClick = onRegisterButtonClick
        )
    }
}

@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    onLoginButtonClick: (String, String)->Unit,
    onRegisterButtonClick: ()->Unit
) {

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false)}

    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthEmailField(
            modifier = modifier.fillMaxWidth().semantics { testTag = "auth-email-field" },
            label = stringResource(id = R.string.email),
            email = email,
            onTextChanged = {
                email = it
                emailError = email.isEmpty()
                            },
            isError = emailError
        )

        if(emailError){
            Text(
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(2.dp)
                    .semantics { testTag="email-error" },
                text = stringResource(id = R.string.email_is_not_valid),
                fontSize = 14.sp,
                color = Color.Red
            )
        }

        AuthFieldPassword(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics { testTag = "auth-password-field" },
            label = stringResource(id = R.string.password),
            password = password,
            passwordHidden = passwordHidden,
            onChangePasswordHidden = {passwordHidden = it},
            onTextChanged = { password = it }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics { testTag = "login-button" },
            onClick = {
                if (email.isNotEmpty()){
                    onLoginButtonClick(email, password)
                }
                emailError = email.isEmpty()
            },
            enabled = !emailError
        ) {
            Text(
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }


        Text(
            modifier= Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.do_not_have_an_account),
            textAlign = TextAlign.Center
        )

        TextButton(
            modifier= Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .semantics { testTag = "register-button" },
            onClick = onRegisterButtonClick,
            content = {Text(text=stringResource(id = R.string.register))}
        )

    }
}




@Composable
fun LoginTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
    )
}

