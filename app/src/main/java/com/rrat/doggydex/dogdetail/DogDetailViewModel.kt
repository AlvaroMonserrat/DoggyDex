package com.rrat.doggydex.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel : ViewModel() {

    private val dogRepository = DogRepository()

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set
/*    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/


    fun addDogToUser(dogId: Long){
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>){
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

}