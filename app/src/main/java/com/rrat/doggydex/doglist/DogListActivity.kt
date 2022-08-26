package com.rrat.doggydex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.dogdetail.DogDetailComposeActivity
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.model.Dog


class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent{
            val status = viewModel.status
            val dogList = viewModel.dogList
            DoggyDexTheme() {
                DogListScreen(
                    dogList = dogList.value,
                    status = status.value,
                    onDogClicked = {startDogDetailActivity(it)},
                    onNavigationIconClick = {onNavigationIconClick()},
                    onDialogDismiss = {resetApiResponseStatus()}
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun startDogDetailActivity(dog: Dog){
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_EXTRA, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick(){
        finish()
    }
}

