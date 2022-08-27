package com.rrat.doggydex.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.doglist.DogRepository
import com.rrat.doggydex.model.Dog
import com.rrat.doggydex.model.User
import kotlinx.coroutines.launch

class AuthViewModel(): ViewModel() {


    private val authRepository = AuthRepository()

    var user = mutableStateOf<User?>(null)
        private set

//    val user: LiveData<User>
//        get() = _user

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set
//    val status: LiveData<ApiResponseStatus<User>>
//        get() = _status


    fun signUp(email: String, password: String, passwordConfirmation: String){

        status.value = ApiResponseStatus.Loading()
        viewModelScope.launch {
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirmation))
        }
    }

    fun signIn(email: String, password: String){
        status.value = ApiResponseStatus.Loading()
        viewModelScope.launch {
            handleResponseStatus(authRepository.signIn(email, password))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            user.value = apiResponseStatus.data
        }
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus(){
        status.value = null
    }
}