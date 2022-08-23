package com.rrat.doggydex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.doglist.DogRepository
import com.rrat.doggydex.machinelearning.Classifier
import com.rrat.doggydex.machinelearning.ClassifierRepository
import com.rrat.doggydex.machinelearning.DogRecognition
import com.rrat.doggydex.model.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel : ViewModel(){

    private val dogRepository = DogRepository()

    private lateinit var classifierRepository: ClassifierRepository


    private val _dog = MutableLiveData<Dog>()

    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    fun setupClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>){
        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }

    fun recognizeImage(imageProxy: ImageProxy){
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizeImage(imageProxy)
            imageProxy.close()
        }
    }

    fun getDogByMlId(mlDogId: String){
        _status.value = ApiResponseStatus.Loading()
        viewModelScope.launch {
            handleResponseStatus(dogRepository.getDogByMlId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if(apiResponseStatus is ApiResponseStatus.Success){
            _dog.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }
}