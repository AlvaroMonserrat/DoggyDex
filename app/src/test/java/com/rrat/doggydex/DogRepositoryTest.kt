package com.rrat.doggydex

import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.core.api.ApiService
import com.rrat.doggydex.core.api.dto.AddDogToUserDTO
import com.rrat.doggydex.core.api.dto.DogDTO
import com.rrat.doggydex.core.api.dto.SignInDTO
import com.rrat.doggydex.core.api.dto.SignUpDTO
import com.rrat.doggydex.core.api.response.*
import com.rrat.doggydex.doglist.DogRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class DogRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDogCollectionSuccess()= runTest{

        class FakeApiService: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(listOf(
                        DogDTO(1,
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
                        ),
                        DogDTO(19,
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
                        )
                    ))
                )
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDTO: SignInDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUSer(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(listOf(
                        DogDTO(19,
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
                        )
                    ))
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepository = DogRepository(
            FakeApiService(),
            dispatcher = UnconfinedTestDispatcher()

        )

        val apiResponseStatus = dogRepository.getDogCollection()

        assert(apiResponseStatus is ApiResponseStatus.Success)

        val dogCollection = (apiResponseStatus as ApiResponseStatus.Success).data
        assertEquals(2, dogCollection.size)
        assertEquals(19L, dogCollection[1].id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetDogCollectionFailure()= runTest{

        class FakeApiService: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDTO: SignInDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUSer(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO()
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        DogDTO(19,
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
                        )
                    )
                )
            }

        }

        val dogRepository = DogRepository(
            FakeApiService(),
            dispatcher = UnconfinedTestDispatcher()

        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals("Network Error",  (apiResponseStatus as ApiResponseStatus.Error).message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDogByMlSuccess()= runTest {
        class FakeApiService: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDTO: SignInDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUSer(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                            DogDTO(19L,
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
                            )
                        )
                    )
            }

        }

        val dogRepository = DogRepository(
            FakeApiService(),
            dispatcher = UnconfinedTestDispatcher()

        )

        val apiResponseStatus = dogRepository.getDogByMlId("")
        assert(apiResponseStatus is ApiResponseStatus.Success)
        assertEquals(19L,  (apiResponseStatus as ApiResponseStatus.Success).data.id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDogByMlFailure()= runTest {
        class FakeApiService: ApiService{
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDTO: SignInDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUSer(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "error_getting_dog_by_ml_id",
                    isSuccess = false,
                    data = DogResponse(
                        DogDTO(19L,
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
                        )
                    )
                )
            }

        }

        val dogRepository = DogRepository(
            FakeApiService(),
            dispatcher = UnconfinedTestDispatcher()

        )

        val apiResponseStatus = dogRepository.getDogByMlId("")
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(UNKNOWN_ERROR,  (apiResponseStatus as ApiResponseStatus.Error).message)
    }
}