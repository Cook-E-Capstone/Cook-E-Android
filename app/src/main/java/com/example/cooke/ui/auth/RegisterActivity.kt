package com.example.cooke.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cooke.R
import com.example.cooke.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

//            register(name, email, password)
        }

        binding.btnLogin.setOnClickListener {
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.ibBack.setOnClickListener {
            finish()
        }

    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}