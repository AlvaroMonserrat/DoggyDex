package com.rrat.doggydex.doglist

import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.api.DogsApi.retrofitService
import com.rrat.doggydex.api.dto.AddDogToUserDTO
import com.rrat.doggydex.api.dto.DogDTOMapper
import com.rrat.doggydex.api.makeNetworkCall
import com.rrat.doggydex.model.Dog

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>>{
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>{
        return makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = retrofitService.addDogToUSer(addDogToUserDTO)

            if(!defaultResponse.isSuccess){
                throw Exception(defaultResponse.message)
            }
        }
    }

    suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

}