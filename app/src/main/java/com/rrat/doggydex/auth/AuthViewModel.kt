package com.rrat.doggydex.auth

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

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status


    fun signUp(email: String, password: String, passwordConfirmation: String){

        _status.value = ApiResponseStatus.Loading()
        viewModelScope.launch {
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirmation))
        }
    }

    fun signIn(email: String, password: String){
        _status.value = ApiResponseStatus.Loading()
        viewModelScope.launch {
            handleResponseStatus(authRepository.signIn(email, password))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            _user.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }
}