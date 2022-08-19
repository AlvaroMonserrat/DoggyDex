package com.rrat.doggydex.auth


import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.api.DogsApi
import com.rrat.doggydex.api.dto.SignInDTO
import com.rrat.doggydex.api.dto.SignUpDTO
import com.rrat.doggydex.api.dto.UserDTOMapper
import com.rrat.doggydex.api.makeNetworkCall
import com.rrat.doggydex.model.User


class AuthRepository {

    suspend fun signUp(email: String, password:String, passwordConfirmation:String): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
            val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

            if(!signUpResponse.isSuccess){
                throw Exception(signUpResponse.message)
            }

            val userDTO= signUpResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUserDomain(userDTO)
        }
    }

    suspend fun signIn(email: String, password:String): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signInDTO = SignInDTO(email, password)
            val authResponse = DogsApi.retrofitService.signIn(signInDTO)

            if(!authResponse.isSuccess){
                throw Exception(authResponse.message)
            }

            val userDTO= authResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUserDomain(userDTO)
        }
    }
}