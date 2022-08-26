package com.rrat.doggydex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.IS_RECOGNITION
import com.rrat.doggydex.R
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.dogdetail.ui.theme.DoggyDexTheme
import com.rrat.doggydex.machinelearning.DogRecognition
import com.rrat.doggydex.model.Dog

class DogDetailComposeActivity : ComponentActivity() {

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent.extras?.getParcelable<Dog>(DOG_EXTRA)

        val isRecognition = intent?.extras?.getBoolean(
            IS_RECOGNITION, false
        ) ?: false

        if (isDogIsNull(dog)) return

        setContent {
            val status = viewModel.status

            if (status.value is ApiResponseStatus.Success) {
                finish()
            } else {
                DoggyDexTheme {
                    if (dog != null) {
                        DogDetailScreen(
                            dog = dog,
                            status = status.value,
                            onButtonClicked = { onButtonClicked(dog.id, isRecognition) },
                            onDialogDismiss = { resetApiResponseStatus()})
                    }
                }
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onButtonClicked(dogId: Long, isRecognition: Boolean) {
        if (isRecognition) {
            viewModel.addDogToUser(dogId)
        } else {
            finish()
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

