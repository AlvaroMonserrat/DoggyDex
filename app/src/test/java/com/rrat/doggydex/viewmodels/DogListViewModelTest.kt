package com.rrat.doggydex.viewmodels

import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.base.BaseUnitTest
import com.rrat.doggydex.doglist.DogListViewModel
import com.rrat.doggydex.doglist.DogTasks
import com.rrat.doggydex.fake.FakeDogRepository
import com.rrat.doggydex.model.Dog
import junit.framework.Assert.assertEquals
import org.junit.Test


class DogListViewModelTest : BaseUnitTest(){


    @Test
    fun downloadDogListStatusesCorrect(){

        class FakeDogRepository : DogTasks{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(listOf(
                    Dog(1,
                        1,
                        "",
                        "",
                        0.0,
                        0.0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        inCollection = false
                    ),
                    Dog(2,
                        2,
                        "",
                        "",
                        0.0,
                        0.0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        inCollection = false
                    )
                ))
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(0,
                    1,
                    "",
                    "",
                    0.0,
                    0.0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    inCollection = false
                ))
            }

        }
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(2, viewModel.dogList.value.size)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListStatusesError(){

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun resetStatusSuccess(){

        class FakeDogRepository : DogTasks{
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error("Error Fake")
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(0,
                    1,
                    "",
                    "",
                    0.0,
                    0.0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    inCollection = false
                ))
            }

        }
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )
        viewModel.resetApiResponseStatus()
        assertEquals(null, viewModel.status.value)
    }
}