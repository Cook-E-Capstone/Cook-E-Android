package com.example.cooke.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cooke.databinding.ActivityDetailRecipeBinding
import com.example.cooke.network.responses.ResultsItem

class DetailRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_RECIPE, ResultsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_RECIPE)
        }

        Log.d(TAG, "onCreate: $recipeData")

        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }
            Glide.with(ivResultImage.context)
                .load(recipeData?.gambarUrl)
                .centerCrop()
                .into(ivResultImage)
            tvName.text = recipeData?.menu

            tvKalori.text = recipeData?.kaloriKkal.toString()
            tvLemak.text = recipeData?.lemakG.toString()
            tvKarbohidrat.text = recipeData?.karbohidratG.toString()
            tvProtein.text = recipeData?.proteinG.toString()
            tvSerat.text = recipeData?.seratG.toString()
            tvKolesterol.text = recipeData?.kolesterolMg.toString()

            tvPorsi.text = recipeData?.hasil

            var bahan = ""
            for (i in recipeData?.bahan!!) {
                bahan += "${i}\n"
            }
            tvBahan.text = bahan

            var langkah = ""
            for (i in recipeData.langkah!!) {
                langkah += "${i}\n"
            }
            Log.d(TAG, "Langkah : \n$langkah")
            tvLangkah.text = langkah
        }
    }

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"
        const val TAG = "DetailRecipeActivity"
    }
}