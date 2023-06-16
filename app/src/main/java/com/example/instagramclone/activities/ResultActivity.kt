package com.example.instagramclone.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityResultBinding
import com.example.instagramclone.network.responses.Nutrition

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(ResultActivity.EXTRA_DATA, Nutrition::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(ResultActivity.EXTRA_DATA)
        }

        binding.apply {
            Glide.with(ivResultImage.context)
                .load(resultData!!.gambarUrl)
                .centerCrop()
                .into(ivResultImage)
            tvName.text = resultData.nama
            tvDesc.text = resultData.deskripsi
            tvKalori.text = resultData.kaloriKkal.toString()
            tvLemak.text = resultData.lemakTotalG.toString()
            tvPotasium.text = resultData.potasiumMg.toString()
            tvSerat.text = resultData.seratG.toString()
            tvSodium.text = resultData.sodiumMg.toString()
            tvGula.text = resultData.gulaG.toString()
            tvProtein.text = resultData.proteinG.toString()
            tvKalsium.text = resultData.kalsiumMg.toString()
            tvBesi.text = resultData.zatBesiMg.toString()
            tvVitaminA.text = resultData.vitaminA.toString()
            tvVitaminE.text = resultData.vitaminE.toString()
            tvVitaminK.text = resultData.vitaminK.toString()
            tvVitaminC.text = resultData.vitaminC.toString()
            tvVitaminB.text = resultData.vitaminBKompleks.toString()
        }

        binding.ivBack.setOnClickListener {
            finish()
//            val intent = Intent(this, PostActivity::class.java)
//            startActivity(intent)
        }
//        if (this is ResultActivity) {
//            binding.bottomNavigationView2.actionScan.setColorFilter(
//                ContextCompat.getColor(this, R.color.red)
//            )
//        } else {
//            binding.bottomNavigationView2.actionScan.clearColorFilter()
//        }
//
//        binding.bottomNavigationView2.actionHome.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.bottomNavigationView2.actionCommunity.setOnClickListener {
//            val intent = Intent(this, CommunityActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.bottomNavigationView2.actionScan.setOnClickListener {
//            val intent = Intent(this, PostActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.bottomNavigationView2.actionRecipe.setOnClickListener {
//            val intent = Intent(this, RecipeActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.bottomNavigationView2.actionNutritionist.setOnClickListener {
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
//        }

    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}