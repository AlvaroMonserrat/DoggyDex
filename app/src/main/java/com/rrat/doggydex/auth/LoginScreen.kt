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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rrat.doggydex.R
import com.rrat.doggydex.composables.AuthEmailField
import com.rrat.doggydex.composables.AuthFieldPassword

@Composable
fun LoginScreen(
    onRegisterButtonClick: ()->Unit
) {
    Scaffold(
        topBar = { LoginTopBar() }
    ) {
        LoginSection(
            modifier = Modifier.padding(it),
            onRegisterButtonClick = onRegisterButtonClick
        )
    }
}

@Composable
fun LoginSection(
    modifier: Modifier = Modifier,
    onRegisterButtonClick: ()->Unit
) {

    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {}
        ) {
            Text(
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }


        Text(
            modifier= Modifier.fillMaxWidth().padding(16.dp),
            text = stringResource(id = R.string.do_not_have_an_account),
            textAlign = TextAlign.Center
        )

        TextButton(
            modifier= Modifier.fillMaxWidth().padding(16.dp),
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

