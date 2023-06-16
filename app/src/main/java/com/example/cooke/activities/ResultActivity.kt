package com.example.cooke.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cooke.databinding.ActivityResultBinding
import com.example.cooke.network.responses.Nutrition

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, Nutrition::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
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
//            tvVitaminA.text = resultData.vitaminA.toString()
//            tvVitaminE.text = resultData.vitaminE.toString()
//            tvVitaminK.text = resultData.vitaminK.toString()
//            tvVitaminC.text = resultData.vitaminC.toString()
//            tvVitaminB.text = resultData.vitaminBKompleks.toString()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}