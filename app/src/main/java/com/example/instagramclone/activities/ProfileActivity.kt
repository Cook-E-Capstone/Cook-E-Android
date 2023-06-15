package com.example.instagramclone.activities

import BottomNavigationView2
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.R
import com.example.instagramclone.UserPreferences
import com.example.instagramclone.databinding.ActivityProfileBinding
import com.example.instagramclone.models.AuthViewModel
import com.example.instagramclone.models.MainViewModel
import com.example.instagramclone.network.ApiConfig
import com.example.instagramclone.network.responses.ProfileResponse
import com.example.instagramclone.network.responses.UserProfile
import com.example.instagramclone.utils.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)


        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref,this,"")).get(
            AuthViewModel::class.java
        )

        authViewModel.getAuthSettings().observe(this) {
            mainViewModel.getUserInfo(it.token)
            mainViewModel.userData.observe(this) {
                setUserData(it)
            }

        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.actionLogout.setOnClickListener {
            authViewModel.removeAuthSetting()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.ivBack.setOnClickListener {
            finish()
        }


    }

    private fun setUserData(userData : UserProfile) {
        binding.apply {
            tvUsername.text = userData.name
            tvEmail.text = userData.email
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val TAG =  "ProfileActivity"
    }
}