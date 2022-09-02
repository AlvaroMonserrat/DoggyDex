package com.rrat.doggydex.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rrat.doggydex.R
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.core.model.Dog


@Composable
fun ProbableDogsDialog(
    mostProbableDogs: MutableList<Dog>,
    onShowMostProbableDogsDialogDismiss: () -> Unit,
    onItemClicked: (Dog) -> Unit
) {
    AlertDialog(
        onDismissRequest = onShowMostProbableDogsDialogDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.other_probable_dogs),
                color = colorResource(id = R.color.text_black),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            MostProbableDogsList(
                dogs = mostProbableDogs,
                onItemClicked = {
                    onShowMostProbableDogsDialogDismiss()
                    onItemClicked(it)
                }
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = onShowMostProbableDogsDialogDismiss) {
                    Text(stringResource(id = R.string.dismiss))
                }
            }
        }
    )
}

@Composable
fun MostProbableDogsList(
    dogs: MutableList<Dog>,
    onItemClicked: (Dog) -> Unit
) {
    Box(
        Modifier.height(250.dp)
    ) {
        LazyColumn(content = {
            items(dogs) {
                MostProbableDogItem(dog = it, onItemClicked = onItemClicked)
            }
        })
    }
}

@Composable
fun MostProbableDogItem(
    dog: Dog,
    onItemClicked: (Dog) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                enabled = true,
                onClick = { onItemClicked(dog) }
            )
    ) {
        Text(
            text = dog.name,
            modifier = Modifier,
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_black)
        )

    }
    Divider(
        Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray))

}

@Preview
@Composable
fun ProbableDogsDialogPreview() {
    DoggyDexTheme() {
        Surface() {
            ProbableDogsDialog(
                mostProbableDogs = mutableListOf<Dog>(
                    Dog(
                        id = 1,
                        index = 1,
                        "Labrador",
                        "big",
                        19.0,
                        20.0,
                        "",
                        "12-15",
                        "2.0",
                        weightFemale = "2.5",
                        weightMale = "12.0",
                        false
                    ),
                    Dog(
                        id = 2,
                        index = 2,
                        "Pastor",
                        "big",
                        19.0,
                        20.0,
                        "",
                        "12-15",
                        "2.0",
                        weightFemale = "2.5",
                        weightMale = "12.0",
                        false
                    ),
                    Dog(
                        id = 2,
                        index = 2,
                        "Dalmata",
                        "big",
                        19.0,
                        20.0,
                        "",
                        "12-15",
                        "2.0",
                        weightFemale = "2.5",
                        weightMale = "12.0",
                        false
                    )
                ),
                onShowMostProbableDogsDialogDismiss = { /*TODO*/ },
                onItemClicked = {}
            )
        }
    }
}