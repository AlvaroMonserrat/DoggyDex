package com.rrat.doggydex.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.rrat.doggydex.*
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.api.ApiServiceInterceptor
import com.rrat.doggydex.auth.LoginActivity
import com.rrat.doggydex.databinding.ActivityMainBinding
import com.rrat.doggydex.dogdetail.DogDetailComposeActivity
import com.rrat.doggydex.doglist.DogListActivity
import com.rrat.doggydex.machinelearning.Classifier
import com.rrat.doggydex.machinelearning.DogRecognition
import com.rrat.doggydex.model.Dog
import com.rrat.doggydex.model.User
import com.rrat.doggydex.settings.SettingsActivity
import com.rrat.doggydex.testutils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.common.FileUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                setupCamera()
            } else {
                Toast.makeText(this,
                    "You need to accept camera permission to use camera",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = User.getLoggedInUser(this)

        if (user == null){
            startLoginActivity()
        }else{
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.dogListFab.setOnClickListener{
            startListDogActivity()
        }

        binding.settingsFab.setOnClickListener {
            startSettingsActivity()
        }


        viewModel.status.observe(this){
                status->
            when(status){
                is ApiResponseStatus.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> binding.progressBar.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.dog.observe(this){
            dog ->
            if(dog != null){
                startDogDetailsActivity(dog)
            }
        }

        viewModel.dogRecognition.observe(this){
            dogRecognition ->
            enabledTakePhotoButton(dogRecognition)
        }

        requestCameraPermission()
    }

    private fun startDogDetailsActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_EXTRA, dog)
        intent.putExtra(MOST_PROBABLE_DOGS_IDS_EXTRA, viewModel.probableDogIds)
        intent.putExtra(IS_RECOGNITION, true)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        if(::cameraExecutor.isInitialized){
            cameraExecutor.shutdown()
        }
    }

    private fun setupCamera(){
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                //.setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }


    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        EspressoIdlingResource.increment()
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                EspressoIdlingResource.decrement()
                viewModel.recognizeImage(imageProxy)
            }

            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun enabledTakePhotoButton(dogRecognition: DogRecognition) {
        if(dogRecognition.confidence > 70.0){
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                viewModel.getDogByMlId(dogRecognition.id)
            }
        }else{
            binding.takePhotoFab.alpha = 0.2f
            binding.takePhotoFab.setOnClickListener(null)
        }
    }



/*    private fun takePhoto(){

//        val localDir = ContextWrapper(this).getDir("images_dogs", Context.MODE_PRIVATE)
//        val photoFile = File(localDir.absolutePath + "/" + "example" + ".jpg")
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()

        Log.i("CAMERA", "HERE")
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    runOnUiThread {

                        Toast.makeText(this@MainActivity,
                            "Photo was taken successfully",
                            Toast.LENGTH_SHORT).show()

                        //val photoUri = outputFileResults.savedUri

                        //val bitmap = BitmapFactory.decodeFile(photoUri?.path)
                       // val dogRecognition = classifier.recognizeImage(bitmap).first()
                        //viewModel.getDogByMlId(dogRecognition.id)
                        //startViewerImageActivity(photoUri.toString())
                    }

                }

                override fun onError(exception: ImageCaptureException) {
                    runOnUiThread {
                        Toast.makeText(baseContext,
                            "Error taking photo ${exception.message}",
                            Toast.LENGTH_SHORT).show()
                    }

                }

            }
        )


    }*/

/*    private fun getOutputPhotoFile(): File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name) + ".jpg").apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()){
            mediaDir
        }else{
            filesDir
        }
    }*/

    private fun startSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun startListDogActivity() {
        val intent = Intent(this, DogListActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

/*    private fun startViewerImageActivity(photoUri: String){
        val intent = Intent(this, ViewerImageActivity::class.java)
        intent.putExtra(PHOTO_URI_EXTRA, photoUri)
        startActivity(intent)
    }*/

    private fun requestCameraPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                setupCamera()

            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
                AlertDialog.Builder(this)
                    .setTitle("Accept please")
                    .setMessage("Do you agree?")
                    .setPositiveButton(android.R.string.ok)
                    {
                        _, _ ->
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    .setNegativeButton(android.R.string.cancel){
                            _, _ ->
                    }.show()

        }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA )
            }
        }
    }
}