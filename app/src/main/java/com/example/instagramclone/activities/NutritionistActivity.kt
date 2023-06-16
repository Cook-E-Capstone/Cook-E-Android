package com.example.instagramclone.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityNutritionistBinding

class NutritionistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutritionistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView2.actionNutritionist.setColorFilter(
            ContextCompat.getColor(this, R.color.red)
        )

        binding.bottomNavigationView2.actionHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView2.actionCommunity.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView2.actionScan.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView2.actionRecipe.setOnClickListener {
            val intent = Intent(this, RecipeActivity::class.java)
            startActivity(intent)
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}