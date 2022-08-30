package com.rrat.doggydex.fake

import androidx.camera.core.ImageProxy
import com.rrat.doggydex.machinelearning.ClassifierTasks
import com.rrat.doggydex.machinelearning.DogRecognition

class FakeClassifierRepository : ClassifierTasks {
    override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
        return DogRecognition("1", 0.5f)
    }
}