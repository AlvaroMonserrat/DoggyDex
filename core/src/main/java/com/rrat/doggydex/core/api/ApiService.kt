package com.rrat.doggydex.core.api

import com.rrat.doggydex.core.*
import com.rrat.doggydex.core.api.dto.AddDogToUserDTO
import com.rrat.doggydex.core.api.dto.SignInDTO
import com.rrat.doggydex.core.api.dto.SignUpDTO
import com.rrat.doggydex.core.api.response.AuthApiResponse
import com.rrat.doggydex.core.api.response.DefaultResponse
import com.rrat.doggydex.core.api.response.DogApiResponse
import com.rrat.doggydex.core.api.response.DogListApiResponse
import retrofit2.http.*


interface ApiService{
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN)
    suspend fun signIn(@Body signInDTO: SignInDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER)
    suspend fun addDogToUSer(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMlId(@Query("ml_id")mlId: String): DogApiResponse
}
