package com.example.cooke.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cooke.R
import com.example.cooke.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

//            login(email, password)
        }

        binding.btnSignup.setOnClickListener {
            finish()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}