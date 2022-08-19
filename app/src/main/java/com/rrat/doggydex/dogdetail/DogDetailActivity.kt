package com.rrat.doggydex.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.model.Dog
import com.rrat.doggydex.R
import com.rrat.doggydex.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent.extras?.getParcelable<Dog>(DOG_EXTRA)

        if (isDogIsNull(dog)) return

        if (dog != null) {
            binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
            binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        }
        binding.dog = dog
        binding.dogImage.load(dog?.imageUrl)

        binding.closeButton.setOnClickListener {
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