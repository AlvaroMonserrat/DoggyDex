package com.rrat.doggydex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rrat.doggydex.api.ApiServiceInterceptor
import com.rrat.doggydex.auth.LoginActivity
import com.rrat.doggydex.databinding.ActivityMainBinding
import com.rrat.doggydex.doglist.DogListActivity
import com.rrat.doggydex.model.User
import com.rrat.doggydex.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
    }

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
}