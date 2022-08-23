package com.rrat.doggydex.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rrat.doggydex.DOG_EXTRA
import com.rrat.doggydex.api.ApiResponseStatus
import com.rrat.doggydex.databinding.ActivityDogListBinding
import com.rrat.doggydex.dogdetail.DogDetailActivity


private const val GRID_SPAN_COUNT = 3

class DogListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogListBinding

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_EXTRA, it)
            startActivity(intent)
        }

        binding.dogRecycler.adapter = adapter
        binding.dogRecycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

        dogListViewModel.dogList.observe(this){
            binding.dogRecycler.adapter = adapter
            adapter.submitList(it)
        }



        dogListViewModel.status.observe(this){
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
    }
}

