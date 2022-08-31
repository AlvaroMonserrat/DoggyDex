package com.rrat.doggydex

import android.Manifest
import androidx.camera.core.ImageProxy
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.di.ClassifierModule
import com.rrat.doggydex.di.DogTasksModule
import com.rrat.doggydex.doglist.DogRepository
import com.rrat.doggydex.doglist.DogTasks
import com.rrat.doggydex.machinelearning.ClassifierRepository
import com.rrat.doggydex.machinelearning.ClassifierTasks
import com.rrat.doggydex.machinelearning.DogRecognition
import com.rrat.doggydex.main.MainActivity
import com.rrat.doggydex.model.Dog
import com.rrat.doggydex.testutils.EspressoIdlingResource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@UninstallModules(DogTasksModule::class, ClassifierModule::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    //Permissions
    //@get:Rule(order = 1)
    //val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @get:Rule(order = 2)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)



    class FakeDogRepository @Inject constructor() : DogTasks {
        override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
            return ApiResponseStatus.Error("Error Fake")
        }

        override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
            return ApiResponseStatus.Success(Unit)
        }

        override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
            return ApiResponseStatus.Success(
                Dog(89,
                    70,
                    "Chow Chow",
                    "",
                    0.0,
                    0.0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    inCollection = true
                )
            )
        }

    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DogTasksTestModule {

        @Binds
        abstract fun bindDogTasks(fakeDogRepository: FakeDogRepository): DogTasks

    }


    class FakeClassifierRepository @Inject constructor() : ClassifierTasks {
        override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
            return DogRecognition("1", 80.0f)
        }
    }

    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ClassifierTestModule {
        @Binds
        abstract fun bindClassifierTasks( fakeClassifierRepository: FakeClassifierRepository): ClassifierTasks
    }

    @Test
    fun showAllFab(){
        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.settings_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun dogListOpensWhenClickingButton(){
        onView(withId(R.id.dog_list_fab)).perform(click())

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val string = context.getString(R.string.my_dog_collection)
        composeTestRule.onNodeWithText(string).assertIsDisplayed()
    }

    @Test
    fun whenRecognizingDogDetailsScreenOpens(){
        onView(withId(R.id.take_photo_fab)).perform(click())

        composeTestRule.onNodeWithTag(testTag = "close-details-screen-fab").assertIsDisplayed()
    }
}