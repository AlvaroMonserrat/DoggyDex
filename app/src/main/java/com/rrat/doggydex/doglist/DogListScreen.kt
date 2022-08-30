package com.rrat.doggydex.doglist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rrat.doggydex.R
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.composables.BackNavigationIcon
import com.rrat.doggydex.composables.ErrorDialog
import com.rrat.doggydex.composables.LoadingWheel
import com.rrat.doggydex.model.Dog


private const val GRID_SPAN_COUNT = 3

@Composable
fun DogListScreen(
    onDogClicked: (Dog) -> Unit,
    onNavigationIconClick: ()->Unit,
    viewModel: DogListViewModel = hiltViewModel()
) {
    val status = viewModel.status.value
    val dogList = viewModel.dogList.value

    Scaffold(
        topBar = {
            DogListScreenTopBar(onClick = onNavigationIconClick)
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(dogList) {
                    DogGridItem(dog = it, onDogClicked = onDogClicked)
                }
            })

    }

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(status.message, onDialogDismiss = {viewModel.resetApiResponseStatus()})
    }

}

@Composable
fun DogListScreenTopBar(onClick: ()->Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.my_dog_collection)) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = {BackNavigationIcon(onClick = onClick)}
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogGridItem(dog: Dog, onDogClicked: (Dog) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp),
            elevation = 8.dp
        ) {
            AsyncImage(
                model = dog.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .background(Color.White)
                    .semantics { testTag = "dog-${dog.name}" }
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Red,
            shape = RoundedCornerShape(4.dp),
            elevation = 8.dp
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = dog.index.toString(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
        }
    }

}

@Composable
fun DogItem(dog: Dog, onDogClicked: (Dog) -> Unit) {

    if (dog.inCollection) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onDogClicked(dog) },
            text = dog.name
        )
    } else {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Red),
            text = stringResource(id = R.string.dog_index_format, dog.index)
        )
    }

}