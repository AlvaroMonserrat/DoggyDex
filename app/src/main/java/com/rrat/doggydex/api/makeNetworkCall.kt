package com.rrat.doggydex.api

import com.rrat.doggydex.*
import com.rrat.doggydex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException


private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T>{
    return withContext(Dispatchers.IO){
        try {
            ApiResponseStatus.Success(call())
        }catch (e: UnknownHostException){
            ApiResponseStatus.Error("Network Error")

        }catch (e: HttpException){
            val errorMessage = if(e.code() == UNAUTHORIZED_ERROR_CODE) {
                ERROR_WRONG_USER_OR_PASS
            } else {
                UNKNOWN_ERROR
            }
            ApiResponseStatus.Error(errorMessage)
        }catch (e: Exception){

            val errorMessage = when(e.message){
                "sign_up_error" -> ERROR_SIGN_UP
                "sign_in_error" -> ERROR_SIGN_IN
                "user_already_exits" -> USER_ALREADY_EXIST
                "error_adding_dog" -> ERROR_ADDING_DOG
                else-> UNKNOWN_ERROR
            }
            ApiResponseStatus.Error(errorMessage)
        }

    }
}