package com.rrat.doggydex.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.IS_RECOGNITION
import com.rrat.doggydex.model.Dog
import com.rrat.doggydex.R
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogDetailBinding

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent.extras?.getParcelable<Dog>(DOG_EXTRA)

        val isRecognition = intent?.extras?.getBoolean(
            IS_RECOGNITION, false) ?: false

        if (isDogIsNull(dog)) return

        if (dog != null) {
            binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
            binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        }
        binding.dog = dog
        binding.dogImage.load(dog?.imageUrl)

        binding.closeButton.setOnClickListener {

            if(isRecognition){
                viewModel.addDogToUser(dog!!.id)
            }else{
                finish()
            }

        }

        viewModel.status.observe(this){
                status->
            when(status){
                is ApiResponseStatus.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> binding.progressBar.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> {
                    binding.progressBar.visibility = View.GONE
                    finish()
                }
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