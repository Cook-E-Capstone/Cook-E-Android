package com.example.instagramclone.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityDetailRecipeBinding
import com.example.instagramclone.network.responses.Nutrition
import com.example.instagramclone.network.responses.ResultsItem

class DetailRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DetailRecipeActivity.EXTRA_RECIPE, ResultsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DetailRecipeActivity.EXTRA_RECIPE)
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

            var bahan: String = ""
            for (i in recipeData?.bahan!!) {
                bahan = bahan + "${i}\n"
            }
            tvBahan.text = bahan

            var langkah: String = ""
            for (i in recipeData?.langkah!!) {
                langkah = langkah + "${i}\n"
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