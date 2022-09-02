package com.rrat.doggydex.dogdetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import coil.compose.AsyncImage
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.R
import com.rrat.doggydex.core.api.ApiResponseStatus
import com.rrat.doggydex.composables.ErrorDialog
import com.rrat.doggydex.composables.LoadingWheel
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.core.model.Dog


@Composable
fun DogDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DogDetailViewModel = hiltViewModel(),
    onFinish: () -> Unit,
) {

    val probableDogsDialogEnabled = remember{ mutableStateOf(false)}
    val status = viewModel.status.value
    val dog = viewModel.dog.value!!
    val isRecognition = viewModel.isRecognition.value

    if (status is ApiResponseStatus.Success) {
        onFinish()
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.secondary_background))
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = modifier
            /*    .background(colorResource(id = R.color.secondary_background))
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),*/,
            contentAlignment = Alignment.TopCenter
        ) {

            DogInformation(dog, isRecognition){
                viewModel.getProbableDogs()
                probableDogsDialogEnabled.value = true
            }

            AsyncImage(
                modifier = Modifier
                    .width(270.dp)
                    .padding(top = 80.dp),
                model = dog.imageUrl,
                contentDescription = dog.name
            )


            if (status is ApiResponseStatus.Loading) {
                LoadingWheel()
            } else if (status is ApiResponseStatus.Error) {
                ErrorDialog(status.message, onDialogDismiss = { viewModel.resetApiResponseStatus() })
            }

            val probableDogList = viewModel.probableDogList.collectAsState().value

            if(probableDogsDialogEnabled.value){
                ProbableDogsDialog(
                    mostProbableDogs = probableDogList,
                    onShowMostProbableDogsDialogDismiss = {
                        probableDogsDialogEnabled.value = false
                    },
                    onItemClicked = {
                        viewModel.updateDog(it)
                    }
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .semantics { testTag = "close-details-screen-fab" },
            onClick = {
                if (isRecognition) {
                    viewModel.addDogToUser()
                } else {
                    onFinish()
                }
                      },
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
        }


    }

}





@Composable
fun DogInformation(
    dog: Dog,
    isRecognition: Boolean,
    onProbableDogsButtonClick: ()->Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    )
    {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = colorResource(id = R.color.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.dog_index_format, dog.index),
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.End
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    text = dog.name,
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
                LifeIcon()
                Text(
                    text = stringResource(
                        id = R.string.dog_life_expectancy_format,
                        dog.lifeExpectancy
                    ),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = dog.temperament,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                if (isRecognition){
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = onProbableDogsButtonClick,
                    ) {
                        Text(
                            text = stringResource(id = R.string.not_your_dog_button),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        ),
                    color = colorResource(id = R.color.divider),
                    thickness = 1.dp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DogDataColumn(
                        modifier = Modifier.weight(1f),
                        gender = stringResource(id = R.string.female),
                        weight = dog.weightFemale,
                        height = dog.heightFemale.toString()
                    )

                    VerticalDivider()

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = dog.type,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.text_black),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = stringResource(id = R.string.group),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.dark_gray),
                            textAlign = TextAlign.Center,
                        )
                    }

                    VerticalDivider()

                    DogDataColumn(
                        modifier = Modifier.weight(1f),
                        gender = stringResource(id = R.string.male),
                        weight = dog.weightMale,
                        height = dog.heightMale.toString()
                    )
                }
            }
        }
    }
}

@Composable
private fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = colorResource(id = R.color.color_primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hearth_white),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp)
            )
        }

        Surface(
            shape = RoundedCornerShape(bottomEnd = 2.dp, topEnd = 2.dp),
            modifier = Modifier
                .width(200.dp)
                .height(6.dp),
            color = colorResource(id = R.color.color_primary)
        ) {

        }
    }
}

@Composable
private fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(42.dp)
            .width(1.dp),
        color = colorResource(id = R.color.divider)
    )
}

@Composable
private fun DogDataColumn(
    modifier: Modifier = Modifier,
    gender: String,
    weight: String,
    height: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = gender,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black),

            )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = weight,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = stringResource(id = R.string.weight),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray),
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = height,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray),
        )
    }
}

@Composable
fun myDogCard(dog: Dog) {
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
                    .align(Alignment.End),
                text = stringResource(id = R.string.dog_index_format, dog.index),
                style = MaterialTheme.typography.h5
            )
            GeneralDogInformation(dog)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(2.dp)
                    .background(colorResource(id = R.color.light_gray))
            )
            SpecificDogInformation(dog)
        }
    }

}

@Composable
fun GeneralDogInformation(dog: Dog) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            text = dog.name,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )
        LifeExpectancyBar()
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            text = "${dog.lifeExpectancy} years",
            style = MaterialTheme.typography.h6
        )

        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            text = dog.temperament,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun LifeExpectancyBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(colorResource(id = R.color.color_primary))
        )
        Canvas(modifier = Modifier.size(24.dp)) {
            drawCircle(color = Color.Red)
        }

        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp),
            painter = painterResource(id = R.drawable.ic_hearth_white),
            contentDescription = null,
            tint = Color.White
        )

    }

}

@Composable
fun SpecificDogInformation(dog: Dog) {
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
            Text(
                text = dog.weightFemale,
                style = MaterialTheme.typography.h6
            )
            Text(text = stringResource(id = R.string.weight))
            Text(
                text = dog.heightFemale.toString(),
                style = MaterialTheme.typography.h6
            )
            Text(text = stringResource(id = R.string.height))
        }
        Spacer(
            modifier = Modifier
                .width(2.dp)
                .height(64.dp)
                .background(colorResource(id = R.color.light_gray))
        )
        Column(
            Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dog.type,
                style = MaterialTheme.typography.h6
            )
            Text(text = stringResource(id = R.string.group))
        }
        Spacer(
            modifier = Modifier
                .width(2.dp)
                .height(64.dp)
                .background(colorResource(id = R.color.light_gray))
        )
        Column(
            Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.male))
            Text(
                text = dog.weightMale,
                style = MaterialTheme.typography.h6
            )
            Text(text = stringResource(id = R.string.weight))
            Text(
                text = dog.heightMale.toString(),
                style = MaterialTheme.typography.h6
            )
            Text(text = stringResource(id = R.string.height))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DogDetailScreenPreview() {
    DoggyDexTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val dog = Dog(
                1L,
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

            val savedStateHandle = SavedStateHandle()
            savedStateHandle[DOG_EXTRA] = dog

            DogDetailScreen(onFinish = {})
        }
    }
}