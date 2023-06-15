package com.example.instagramclone.activities

import BottomNavigationView2
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.R
import com.example.instagramclone.UserPreferences
import com.example.instagramclone.adapters.CommunityPostAdapter
import com.example.instagramclone.databinding.ActivityCommunityBinding
import com.example.instagramclone.models.AuthViewModel
import com.example.instagramclone.models.MainViewModel
import com.example.instagramclone.network.responses.CommunityItem
import com.example.instagramclone.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    private var token : String = ""

//    private val mainViewModel by viewModels<MainViewModel>()


    companion object {
        const val EXTRA_USER = "extra_user"
        const val TAG = "MODAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref,this,"")).get(
            AuthViewModel::class.java
        )

        authViewModel.getAuthSettings().observe(this) {
            token = it.token
            if (it.token.isEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



//
        authViewModel.getAuthSettings().observe(this) { authData ->
            mainViewModel.getAllPost(authData.token)
            mainViewModel.listPost.observe(this) {
                Log.d(TAG, "onCreate: ${it}")

                setPostData(it)
            }
        }



        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


////
        binding.actionPost.setOnClickListener {
            val postIntent = Intent(this, CommunityPostActivity::class.java)
            startActivity(postIntent)
        }


        if (this is CommunityActivity) {
            binding.bottomNavigationView2.actionCommunity.setColorFilter(
                ContextCompat.getColor(this, R.color.red)
            )
        } else {
            binding.bottomNavigationView2.actionCommunity.clearColorFilter()
        }

        binding.bottomNavigationView2.actionHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        binding.bottomNavigationView2.actionCommunity.setOnClickListener {
//            val intent = Intent(this, CommunityActivity::class.java)
//            startActivity(intent)
//        }

        binding.bottomNavigationView2.actionScan.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView2.actionRecipe.setOnClickListener {
            val intent = Intent(this, RecipeActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView2.actionNutritionist.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

//    private fun setupViewModel() {
//        val pref = UserPreferences.getInstance(dataStore)
//
//        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref,this,"")).get(
//            AuthViewModel::class.java
//        )
//        authViewModel.getAuthSettings().observe(this) { authData ->
//            ViewModelProvider(this,ViewModelFactory(UserPreferences.getInstance(dataStore),this@MainActivity,authData.token))[MainViewModel::class.java]
//
//        }
//    }

//    private fun getAllStories() {
//        val listStoryAdapter = ListStoryAdapter()
//
//        binding.rvStory.adapter = listStoryAdapter.withLoadStateFooter(
//            footer = LoadingStateAdapter{
//                listStoryAdapter.retry()
//                Log.d("mathoriq", "getAllStories: line 125")
//            }
//        )
//        mainViewModel.stories.observe(this) {story ->
//            listStoryAdapter.submitData(lifecycle, story)
//        }
//
//
//    }

//    private fun setStoryData(listStory : List<ListStoryItem>) {
//        val adapter = StoryAdapter(listStory)
//        binding.rvStory.adapter = adapter
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }


    private fun setPostData(listPost : List<CommunityItem>) {
        val adapter = CommunityPostAdapter(listPost)
        binding.rvStory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



}