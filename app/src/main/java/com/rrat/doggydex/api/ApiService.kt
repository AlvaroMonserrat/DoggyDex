package com.rrat.doggydex.api

import com.rrat.doggydex.*
import com.rrat.doggydex.api.dto.AddDogToUserDTO
import com.rrat.doggydex.api.dto.SignInDTO
import com.rrat.doggydex.api.dto.SignUpDTO
import com.rrat.doggydex.api.response.DogListApiResponse
import com.rrat.doggydex.api.response.AuthApiResponse
import com.rrat.doggydex.api.response.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()


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
    @POST(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse
}


object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}