package com.example.instagramclone.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.R
import com.example.instagramclone.UserPreferences
import com.example.instagramclone.adapters.ListRecipeAdapter
import com.example.instagramclone.databinding.ActivityRecipeBinding
import com.example.instagramclone.models.AuthViewModel
import com.example.instagramclone.models.MainViewModel
import com.example.instagramclone.network.responses.ResultsItem
import com.example.instagramclone.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private var token : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref,this,"")).get(
            AuthViewModel::class.java
        )

        authViewModel.getAuthSettings().observe(this) { authData ->
            mainViewModel.searchRecipe(authData.token,"ayam", limit = 20)
            mainViewModel.recipeData.observe(this) {
//                Log.d("RECIPE", "onCreate: ${it}")

                setRecipeData(it)
            }

            binding.actionSearch.setOnClickListener {
                mainViewModel.searchRecipe(authData.token, binding.searchEditText.text.toString(), 10)
                mainViewModel.recipeData.observe(this) {
                    Log.d("RECIPE", "data : $it")
                    setRecipeData(it)
                }
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isEmpty.observe(this) {
            showEmpty(it)
        }

    }

    private fun setRecipeData(listRecipe : List<ResultsItem>) {
        val adapter = ListRecipeAdapter(listRecipe)
        binding.rvStory.adapter = adapter
    }


    private fun showEmpty(isEmpty: Boolean) {
        binding.tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}