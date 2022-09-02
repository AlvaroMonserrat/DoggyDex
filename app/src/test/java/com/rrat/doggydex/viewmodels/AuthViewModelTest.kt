package com.rrat.doggydex.viewmodels

import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.auth.AuthTasks
import com.rrat.doggydex.auth.AuthViewModel
import com.rrat.doggydex.base.BaseUnitTest
import com.rrat.doggydex.core.model.User
import junit.framework.Assert.assertEquals
import org.junit.Test

class AuthViewModelTest : BaseUnitTest() {

    @Test
    fun loginSuccess(){
        class AuthRepositoryFake: AuthTasks{
            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1L, email, "abc"))
            }

            override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(User(1L, email, "abc"))
            }

        }

        val viewModel = AuthViewModel(AuthRepositoryFake())

        viewModel.signIn("example@gmail.com", "1234")

        assertEquals("example@gmail.com", viewModel.user.value?.email)
    }
}