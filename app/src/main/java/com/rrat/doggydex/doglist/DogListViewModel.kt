package com.rrat.doggydex.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.core.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val dogRepository: DogTasks): ViewModel() {

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    init {
       getDogCollection()
    }


    private fun getDogCollection(){
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            dogList.value = apiResponseStatus.data
        }
        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }



}