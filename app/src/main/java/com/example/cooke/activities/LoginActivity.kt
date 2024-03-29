package com.example.cooke.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.cooke.UserPreferences
import com.example.cooke.databinding.ActivityLoginBinding
import com.example.cooke.models.AuthViewModel
import com.example.cooke.network.ApiConfig
import com.example.cooke.network.responses.CookeLoginResponse
import com.example.cooke.utils.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel =
            ViewModelProvider(this@LoginActivity, ViewModelFactory(pref, this@LoginActivity,""))[AuthViewModel::class.java]

        authViewModel.getAuthSettings().observe(this) {
            if (it.token.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            login(email, password)
        }

        binding.tvToRegister.setOnClickListener {
            finish()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


    }

    private fun login(email : String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().login(email,password)
        client.enqueue(object : Callback<CookeLoginResponse> {
            override fun onResponse(call: Call<CookeLoginResponse>, response: Response<CookeLoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val pref = UserPreferences.getInstance(dataStore)
                    val authViewModel = ViewModelProvider(this@LoginActivity, ViewModelFactory(pref, this@LoginActivity,""))[AuthViewModel::class.java]

                    authViewModel.saveAuthSetting(responseBody.data?.user?.name!!, responseBody.data.user.id!!, responseBody.data.token!!)
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, "Login succeeded", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Log.e(TAG, "onFailure: $response")
                    showLoading(false)
                    binding.edLoginPassword.error = "Invalid username or password"
                }
            }

            override fun onFailure(call: Call<CookeLoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT).show()

                Log.e(TAG, t.message.toString())
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

