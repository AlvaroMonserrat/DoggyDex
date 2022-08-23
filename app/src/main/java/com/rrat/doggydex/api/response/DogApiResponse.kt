package com.rrat.doggydex.api.response

import com.rrat.doggydex.api.dto.DogDTO
import com.squareup.moshi.Json

class DogApiResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: DogResponse
)