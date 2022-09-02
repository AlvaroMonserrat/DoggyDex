package com.rrat.doggydex.fake

import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.doglist.DogTasks
import com.rrat.doggydex.core.model.Dog

class FakeDogRepository : DogTasks {
    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return ApiResponseStatus.Error("Error Fake")
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
        return ApiResponseStatus.Success(Unit)
    }

    override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
        return ApiResponseStatus.Success(
            Dog(0,
            1,
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
        )
    }

}