package com.rrat.doggydex

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.doglist.DogListActivity
import com.rrat.doggydex.doglist.DogListScreen
import com.rrat.doggydex.doglist.DogListViewModel
import com.rrat.doggydex.doglist.DogTasks
import com.rrat.doggydex.model.Dog
import org.junit.Rule
import org.junit.Test

class DogListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun progressBarShowsWhenLoadingState(){

        class FakeDogRepository: DogTasks{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Loading()
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(FakeDogRepository())

        composeTestRule.setContent {
            DogListScreen(
                onDogClicked = {},
                onNavigationIconClick = { /*TODO*/ },
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "loading-wheel").assertIsDisplayed()
    }

    @Test
    fun errorDialogShowsIfErrorGettingDogs(){

        class FakeDogRepository: DogTasks{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error("Error showing dog, dog not found")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(FakeDogRepository())

        composeTestRule.setContent {
            DogListScreen(
                onDogClicked = {},
                onNavigationIconClick = { /*TODO*/ },
                viewModel = viewModel
            )
        }

        val continueLabel = composeTestRule.activity.resources.getString(R.string.error_showing_dog_not_found)
        composeTestRule.onNodeWithText(text = continueLabel).assertIsDisplayed()
    }

    @Test
    fun dogListShowIfSuccessGettingDogs(){

        val dogNameTest1 = "Terry"
        val dogNameTest2 = "Bob"

        class FakeDogRepository: DogTasks{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(1,
                            1,
                            dogNameTest1,
                            "",
                            0.0,
                            0.0,
                            "",
                            "",
                            "",
                            "",
                            "",
                            inCollection = true
                        ),
                        Dog(2,
                            2,
                            dogNameTest2,
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
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = DogListViewModel(FakeDogRepository())

        composeTestRule.setContent {
            DogListScreen(
                onDogClicked = {},
                onNavigationIconClick = { /*TODO*/ },
                viewModel = viewModel
            )
        }

        //composeTestRule.onRoot(useUnmergedTree = true).printToLog("APPLE")
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-${dogNameTest1}").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-${dogNameTest2}").assertIsDisplayed()
    }
}