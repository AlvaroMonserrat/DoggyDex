package com.rrat.doggydex.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.model.Dog
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {

    private val dogRepository = DogRepository()

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set

/*    val dogList: LiveData<List<Dog>?>
        get() = _dogList*/

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set
/*    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/

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