package com.rrat.doggydex.machinelearning

import android.graphics.Bitmap
import com.rrat.doggydex.MAX_RECOGNITION_DOG_RESULTS
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.DequantizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.MappedByteBuffer
import java.util.*
import kotlin.math.min


class Classifier(tfLiteModel: MappedByteBuffer,
                 private val labels: List<String>) {

    /**
     * Image size along the x axis
     */
    private val imageSizeX: Int

    /**
     * Image size along the y axis
     */
    private val imageSizeY: Int


    /**
     * Optional GPU delegate for acceleration.
     */
    private var tfLite: Interpreter


    private var inputImageBuffer: TensorImage


    private var outputProbabilityBuffer: TensorBuffer

    private val tensorProcessor: TensorProcessor

    init {
        val tfLiteOptions = Interpreter.Options()
        //tfLiteOptions.numThreads = 5
        tfLite = Interpreter(tfLiteModel, tfLiteOptions)

        //Read type and shape of input and output tensors, respectively
        val imageTensorIndex = 0
        val imageShape = tfLite.getInputTensor(imageTensorIndex).shape() //[1 224 224 3]
        imageSizeY = imageShape[1]
        imageSizeX = imageShape[2]
        val imageDataType = tfLite.getInputTensor(imageTensorIndex).dataType() //UINT8

        val probabilityTensorIndex = 0
        val probabilityShape = tfLite.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
        val probabilityDataType = tfLite.getOutputTensor(probabilityTensorIndex).dataType()

        //Creates the input tensor
        inputImageBuffer = TensorImage(imageDataType)

        //Creates the output tensor and its processor
        outputProbabilityBuffer = TensorBuffer.createFixedSize(
            probabilityShape,
            probabilityDataType
        )

        tensorProcessor = TensorProcessor.Builder().add(DequantizeOp(0f, 1 / 255.0f)).build()
    }

    /**
     * Runs inference and returns the classification results.
     * */
    fun recognizeImage(bitmap: Bitmap): List<DogRecognition>{
        inputImageBuffer = loadImage(bitmap)
        val rewoundOutputBuffer = outputProbabilityBuffer.buffer.rewind()
        tfLite.run(inputImageBuffer.buffer, rewoundOutputBuffer)

        //Gets the map of label and probability
        val labeledProbability =
            TensorLabel(labels, tensorProcessor.process(outputProbabilityBuffer)).mapWithFloatValue

        return getTopKProbability(labeledProbability)
    }


    /**
     * Loads input image, and applies pre processing
     * */
    private fun loadImage(bitmap: Bitmap): TensorImage{
        //Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap)

        //Creates processor for the TensorImage
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .build()

        return imageProcessor.process(inputImageBuffer)
    }


    companion object{

        private fun getTopKProbability(labelProb: Map<String, Float>): List<DogRecognition>{
            val priorityQueue = PriorityQueue(MAX_RECOGNITION_DOG_RESULTS){
                lhs: DogRecognition, rhs: DogRecognition ->
                (rhs.confidence).compareTo(lhs.confidence)
            }

            for((key, value) in labelProb){
                priorityQueue.add(DogRecognition(key, value * 100.0f))
            }

            val recognitions = mutableListOf<DogRecognition>()
            val recognitionSIze = minOf(priorityQueue.size, MAX_RECOGNITION_DOG_RESULTS)
            for(i in 0 until recognitionSIze){
                recognitions.add(priorityQueue.poll()!!)
            }

            return recognitions
        }
    }

}