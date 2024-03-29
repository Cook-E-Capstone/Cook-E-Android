package com.example.cooke.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.cooke.UserPreferences
import com.example.cooke.databinding.ActivityRegisterBinding
import com.example.cooke.models.AuthViewModel
import com.example.cooke.network.ApiConfig
import com.example.cooke.network.responses.CookeRegisterResponse
import com.example.cooke.utils.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    companion object {
        private const val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnRegister.setOnClickListener {
                val name = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()

                register(name, email, password)
            }
        }
        binding.tvToLogin.setOnClickListener {
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register(name: String, email: String, password: String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || binding.edRegisterEmail.error != null || binding.edRegisterName.error != null|| binding.edRegisterPassword.error != null) {
            Toast.makeText(this@RegisterActivity,"Invalid credentials", Toast.LENGTH_SHORT)
                .show()
        } else {
            showLoading(true)
            val client = ApiConfig.getApiService().register(email, name, password)
            client.enqueue(object : Callback<CookeRegisterResponse> {
                override fun onResponse(
                    call: Call<CookeRegisterResponse>,
                    response: Response<CookeRegisterResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        Log.e(TAG, responseBody.toString())
                        val pref = UserPreferences.getInstance(dataStore)
                        val authViewModel =
                            ViewModelProvider(this@RegisterActivity, ViewModelFactory(pref, this@RegisterActivity,""))[AuthViewModel::class.java]

                        authViewModel.saveAuthSetting(responseBody.data?.user?.name!!, responseBody.data.user.id!!, responseBody.data.token!!)
                        showLoading(false)
                        Toast.makeText(this@RegisterActivity, "User created", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()

                    } else {
                        showLoading(false)
                        Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT)
                            .show()
                        Log.e(TAG, "onFailure: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<CookeRegisterResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.e(TAG, t.message.toString())
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
