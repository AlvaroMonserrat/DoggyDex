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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DogListActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent{
            DoggyDexTheme() {
                DogListScreen(
                    onDogClicked = {startDogDetailActivity(it)},
                    onNavigationIconClick = {onNavigationIconClick()},
                )
            }
        }
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

