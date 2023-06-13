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
        binding.myFloatingButton.setOnClickListener {
            val postIntent = Intent(this, PostActivity::class.java)
            startActivity(postIntent)
        }
//
//        binding.actionMap.setOnClickListener {
//            val mapIntent = Intent(this, MapsActivity::class.java)
//            mainViewModel.listStory.observe(this) {
//                Log.d(TAG, it.toString())
//                mapIntent.putParcelableArrayListExtra("extra_story", ArrayList(it))
//            }
//            startActivity(mapIntent)
//
//        }

//        binding.actionLogout.setOnClickListener {
//            authViewModel.removeAuthSetting()
//        }

        // NOTES : INI CUMA COBA2 SAJA,
        // NANTI DIGANTI KE PROFILE ACTIVITY

        binding.actionProfile.setOnClickListener {
            val communityIntent = Intent(this, CommunityActivity::class.java)
            startActivity(communityIntent)
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



















