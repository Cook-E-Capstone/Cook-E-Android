package com.example.instagramclone.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.ListStoryItem
import com.example.instagramclone.UserPreferences
import com.example.instagramclone.adapters.CommunityPostAdapter
import com.example.instagramclone.adapters.ListStoryAdapter
import com.example.instagramclone.adapters.LoadingStateAdapter
import com.example.instagramclone.adapters.StoryAdapter
import com.example.instagramclone.databinding.ActivityMainBinding
import com.example.instagramclone.models.AuthViewModel
import com.example.instagramclone.models.MainViewModel
import com.example.instagramclone.network.responses.CommunityItem
import com.example.instagramclone.utils.ViewModelFactory
import java.util.regex.Pattern

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token : String = ""

//    private val mainViewModel by viewModels<MainViewModel>()


    companion object {
        const val EXTRA_USER = "extra_user"
        const val TAG = "MODAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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

//        setupViewModel()
//        getAllStories()


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
        binding.actionScan.setOnClickListener {
            val postIntent = Intent(this, PostActivity::class.java)
            startActivity(postIntent)
        }
//
<<<<<<< Updated upstream
//        binding.actionMap.setOnClickListener {
//            val mapIntent = Intent(this, MapsActivity::class.java)
=======
        binding.actionRecipe.setOnClickListener {
            val recipeIntent = Intent(this, RecipeActivity::class.java)
>>>>>>> Stashed changes
//            mainViewModel.listStory.observe(this) {
//                Log.d(TAG, it.toString())
//                mapIntent.putParcelableArrayListExtra("extra_story", ArrayList(it))
//            }
//            startActivity(mapIntent)
//
//        }

        binding.actionCommunity.setOnClickListener {
            val communityIntent = Intent(this, CommunityActivity::class.java)
            startActivity(communityIntent)
        }

//        binding.actionLogout.setOnClickListener {
//            authViewModel.removeAuthSetting()
//        }

<<<<<<< Updated upstream
=======
        // NOTES : INI CUMA COBA2 SAJA,
        // NANTI DIGANTI KE PROFILE ACTIVITY



        // SLIDER

        // on below line we are initializing our slier view.
        sliderView = findViewById(R.id.slider)

        // on below line we are initializing
        // our image url array list.
        imageUrl = ArrayList()

        // on below line we are adding data to our image url array list.
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdsa-self-paced-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdata-science-live-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Ffull-stack-node-thumbnail.png&w=1920&q=75") as ArrayList<String>

        // on below line we are initializing our
        // slider adapter and adding our list to it.
        sliderAdapter = SliderAdapter( imageUrl)

        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        sliderView.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        sliderView.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        sliderView.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        sliderView.startAutoCycle()

>>>>>>> Stashed changes


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    private fun setPostData(listPost : List<CommunityItem>) {
        val adapter = CommunityPostAdapter(listPost)
        binding.rvStory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



}




















