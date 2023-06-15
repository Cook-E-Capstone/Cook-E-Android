package com.example.instagramclone.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
            ivBack.setOnClickListener {
                finish()
            }
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



        }

    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}