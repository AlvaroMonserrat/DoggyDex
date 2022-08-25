package com.rrat.doggydex.dogdetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rrat.doggydex.R
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
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
    
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        backgroundColor = colorResource(id = R.color.white)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.End)
                ,
                text = "#${dog.id}",
                style = MaterialTheme.typography.h5
            )
            GeneralDogInformation(dog)
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(2.dp)
                .background(colorResource(id = R.color.light_gray)))
            SpecificDogInformation(dog)
        }
    }
    
}

@Composable
fun GeneralDogInformation(dog: Dog){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = dog.name,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )
        LifeExpectancyBar()
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = "${dog.lifeExpectancy} years",
            style = MaterialTheme.typography.h6
        )

        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = dog.temperament,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun LifeExpectancyBar(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 64.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(colorResource(id = R.color.color_primary))
        )
        Canvas(modifier = Modifier.size(24.dp)) {
            drawCircle(color = Color.Red)
        }

        Icon(
            modifier= Modifier
                .size(24.dp)
                .padding(4.dp),
            painter = painterResource(id = R.drawable.ic_hearth_white),
            contentDescription = null,
            tint = Color.White
        )

    }

}

@Composable
fun SpecificDogInformation(dog: Dog){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.female))
            Text(text = dog.weightFemale,
                style = MaterialTheme.typography.h6)
            Text(text = stringResource(id = R.string.weight))
            Text(text = dog.heightFemale.toString(),
                style = MaterialTheme.typography.h6)
            Text(text = stringResource(id = R.string.height))
        }
        Spacer(modifier = Modifier
            .width(2.dp)
            .height(64.dp)
            .background(colorResource(id = R.color.light_gray)))
        Column(
            Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = dog.type,
                style = MaterialTheme.typography.h6)
            Text(text = stringResource(id = R.string.group))
        }
        Spacer(modifier = Modifier
            .width(2.dp)
            .height(64.dp)
            .background(colorResource(id = R.color.light_gray)))
        Column(
            Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.male))
            Text(text = dog.weightMale,
                style = MaterialTheme.typography.h6)
            Text(text = stringResource(id = R.string.weight))
            Text(text = dog.heightMale.toString(),
                style = MaterialTheme.typography.h6)
            Text(text = stringResource(id = R.string.height))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DogDetailScreenPreview(){
    DoggyDexTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DogDetailScreen()
        }
    }
}