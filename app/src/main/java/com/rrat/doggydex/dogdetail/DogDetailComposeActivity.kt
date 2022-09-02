package com.rrat.doggydex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rrat.doggydex.R
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.core.model.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailComposeActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            DoggyDexTheme {
                DogDetailScreen(onFinish = { finish() })
            }
        }
    }


    private fun isDogIsNull(dog: Dog?): Boolean {
        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return true
        }
        return false
    }
}

