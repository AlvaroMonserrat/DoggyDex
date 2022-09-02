package com.rrat.doggydex.auth


import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.core.api.ApiService
import com.rrat.doggydex.core.api.dto.SignInDTO
import com.rrat.doggydex.core.api.dto.SignUpDTO
import com.rrat.doggydex.core.api.dto.UserDTOMapper
import com.rrat.doggydex.core.api.makeNetworkCall
import com.rrat.doggydex.core.model.User
import javax.inject.Inject


interface AuthTasks{
    suspend fun signUp(email: String, password:String, passwordConfirmation:String): ApiResponseStatus<User>
    suspend fun signIn(email: String, password:String): ApiResponseStatus<User>
}

class AuthRepository @Inject constructor(
    private val apiService: ApiService
): AuthTasks{

    override suspend fun signUp(email: String, password:String, passwordConfirmation:String): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
            val signUpResponse = apiService.signUp(signUpDTO)

            if(!signUpResponse.isSuccess){
                throw Exception(signUpResponse.message)
            }

            val userDTO= signUpResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUserDomain(userDTO)
        }
    }

    override suspend fun signIn(email: String, password:String): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signInDTO = SignInDTO(email, password)
            val authResponse = apiService.signIn(signInDTO)

            if(!authResponse.isSuccess){
                throw Exception(authResponse.message)
            }

            val userDTO= authResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUserDomain(userDTO)
        }
    }
}