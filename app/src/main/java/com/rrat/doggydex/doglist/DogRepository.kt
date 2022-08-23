package com.rrat.doggydex.doglist

import com.rrat.doggydex.UNKNOWN_ERROR
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.api.DogsApi.retrofitService
import com.rrat.doggydex.api.dto.AddDogToUserDTO
import com.rrat.doggydex.api.dto.DogDTOMapper
import com.rrat.doggydex.api.makeNetworkCall
import com.rrat.doggydex.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {


    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>{
        return withContext(Dispatchers.IO){
            val allDogsListDeferred = async{ downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if(allDogsListResponse is ApiResponseStatus.Error){
                allDogsListResponse
            }else if(userDogsListResponse is ApiResponseStatus.Error){
                userDogsListResponse
            }else if(allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success)
            {
                ApiResponseStatus.Success(getCollectionList(allDogsListResponse.data, userDogsListResponse.data))
            }else{
                ApiResponseStatus.Error(UNKNOWN_ERROR)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog>{
        return allDogList.map {
            if(userDogList.contains(it)){
                it
            }else{
                Dog(0,
                    it.index,
                    "",
                    "",
                    0.0,
                    0.0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    inCollection = false
                )
            }
        }.sorted()
    }


    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>>{
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getUserDogs()
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


    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog>{

        return makeNetworkCall {
            val response = retrofitService.getDogByMlId(mlDogId)
            val dogDTO = response.data.dog
            DogDTOMapper().fromDogDTOToDogDomain(dogDTO)
        }

    }



}