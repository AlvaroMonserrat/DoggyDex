package com.rrat.doggydex.dogdetail

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.IS_RECOGNITION
import com.rrat.doggydex.MOST_PROBABLE_DOGS_IDS_EXTRA
import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.doglist.DogTasks
import com.rrat.doggydex.core.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogTasks,
    savedStateHandle: SavedStateHandle

    ) : ViewModel() {

    var dog = mutableStateOf(
        savedStateHandle.get<Dog>(DOG_EXTRA)
    )
        private set

    @SuppressLint("MutableCollectionMutableState")
    private var probableDogsIds = mutableStateOf(
        savedStateHandle.get<ArrayList<String>>(MOST_PROBABLE_DOGS_IDS_EXTRA) ?: arrayListOf()
    )


    var isRecognition = mutableStateOf(
        savedStateHandle.get<Boolean>(IS_RECOGNITION) ?: false
    )

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set
/*    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/

    private var _probableDogList = MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList: StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun getProbableDogs(){
        _probableDogList.value.clear()
        viewModelScope.launch {
            dogRepository.getProbableDogs(probableDogsIds.value)
                //.catch {}
                .collect{
                apiResponseStatus ->
                if(apiResponseStatus is ApiResponseStatus.Success){
                    val probableDogMutableList = _probableDogList.value.toMutableList()
                    probableDogMutableList.add(apiResponseStatus.data)
                    _probableDogList.value = probableDogMutableList
                }
            }
        }
    }

    fun updateDog(newDog: Dog){
        dog.value = newDog
    }

    fun addDogToUser(){
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dog.value!!.id))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>){
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }


}