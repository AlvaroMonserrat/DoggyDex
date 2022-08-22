package com.rrat.doggydex

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.rrat.doggydex.databinding.ActivityViewerImageBinding
import java.io.File

class ViewerImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewerImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewerImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_EXTRA) ?: ""

        val uri = Uri.parse(photoUri)

        val path = uri.path

        if(path == null){
            Toast.makeText(this, "Error showing iamge no photo uri", Toast.LENGTH_SHORT).show()
            finish()
            return
        }else{
            binding.imageUri.load(File(path))
        }


    }
}