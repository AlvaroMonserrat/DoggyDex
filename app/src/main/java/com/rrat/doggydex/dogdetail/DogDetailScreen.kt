package com.rrat.doggydex.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.rrat.doggydex.R
import com.rrat.doggydex.model.Dog


@Composable
fun DogDetailScreen(
    modifier: Modifier = Modifier
){
    
    Box(modifier = modifier
        .background(colorResource(id = R.color.secondary_background))
        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {

        val dog = Dog(1L,
            78,
            "Pug",
            "Herding",
            70.0,
            75.0,
            "",
            "10 - 12",
            "Friendly, playful",
            "5",
            "6"
        )
        DogInformation(dog)

    }
    
}

@Composable
fun DogInformation(dog: Dog) {
    TODO("Not yet implemented")

}
