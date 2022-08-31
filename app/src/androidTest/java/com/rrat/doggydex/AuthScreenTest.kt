package com.rrat.doggydex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.auth.AuthScreen
import com.rrat.doggydex.auth.AuthTasks
import com.rrat.doggydex.auth.AuthViewModel
import com.rrat.doggydex.model.User
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testTappingRegisterButtonOpenSignUpScreen(){

        class FakeAuthRepository: AuthTasks{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = AuthViewModel(FakeAuthRepository())

        composeTestRule.setContent {
            AuthScreen(onAutoLogin = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTag = "register-button" ).performClick()
        composeTestRule.onNodeWithTag(testTag = "sign-up-button").assertIsDisplayed()
    }

    @Test
    fun testEmailErrorShowsIfTappingLoginButtonAndNotEmail(){

        class FakeAuthRepository: AuthTasks{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = AuthViewModel(FakeAuthRepository())

        composeTestRule.setContent {
            AuthScreen(onAutoLogin = {}, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true,testTag = "auth-email-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true,testTag = "email-error").assertIsDisplayed()
    }
}