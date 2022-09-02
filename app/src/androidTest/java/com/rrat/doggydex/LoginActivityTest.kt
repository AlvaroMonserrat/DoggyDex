package com.rrat.doggydex

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.auth.AuthTasks
import com.rrat.doggydex.auth.LoginActivity
import com.rrat.doggydex.di.AuthTasksModule
import com.rrat.doggydex.core.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@UninstallModules(AuthTasksModule::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    //Permissions
    //@get:Rule(order = 1)
    //val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    class FakeAuthRepository @Inject constructor(): AuthTasks{
        override suspend fun signUp(
            email: String,
            password: String,
            passwordConfirmation: String
        ): ApiResponseStatus<User> {
            TODO("Not yet implemented")
        }

        override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> {
            return ApiResponseStatus.Success(User(1L, "alvaro.monserrat@gmail.com", "asdadg"))
        }

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class AuthTasksTestModule {

        @Binds
        abstract fun bindAuthTasks(fakeAuthRepository: FakeAuthRepository): AuthTasks

    }

    @Test
    fun mainActivityOpensAfterUserLogin(){
        val context = composeTestRule.activity

        composeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(useUnmergedTree = true, testTag = "auth-email-field")
            .performTextInput("alvaro.monserrat@gmail.com")

        composeTestRule
            .onNodeWithTag(useUnmergedTree = true, testTag = "auth-password-field")
            .performTextInput("test1234")

        composeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .performClick()

        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
    }


}