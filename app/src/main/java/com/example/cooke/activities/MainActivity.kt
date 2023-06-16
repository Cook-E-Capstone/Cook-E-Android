package com.example.cooke.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cooke.R
import com.example.cooke.UserPreferences
import com.example.cooke.adapters.CommunityPostAdapter
import com.example.cooke.adapters.SliderAdapter
import com.example.cooke.databinding.ActivityMainBinding
import com.example.cooke.models.AuthViewModel
import com.example.cooke.models.MainViewModel
import com.example.cooke.network.responses.CommunityItem
import com.example.cooke.utils.ViewModelFactory
import com.smarteist.autoimageslider.SliderView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token : String = ""

    // on below line we are creating a variable
    // for our array list for storing our images.
    private lateinit var imageUrl: ArrayList<String>

    // on below line we are creating
    // a variable for our slider view.
    private lateinit var sliderView: SliderView

    // on below line we are creating
    // a variable for our slider adapter.
    private lateinit var sliderAdapter: SliderAdapter

//    private val mainViewModel by viewModels<MainViewModel>()


    companion object {
        const val TAG = "MODAN"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref,this,""))[AuthViewModel::class.java]

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
                Log.d(TAG, "onCreate: $it")

                setPostData(it.take(5))
            }
        }



        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }



        binding.bottomNavigationView2.actionHome.setColorFilter(
            ContextCompat.getColor(this, R.color.red)
        )

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

        binding.bottomNavigationView2.actionNutritionist.setOnClickListener {
            val intent = Intent(this, NutritionistActivity::class.java)
            startActivity(intent)
        }


        binding.actionProfile.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
        }


        // SLIDER

        // on below line we are initializing our slier view.
        sliderView = findViewById(R.id.slider)

        // on below line we are initializing
        // our image url array list.
        imageUrl = ArrayList()

        // on below line we are adding data to our image url array list.
        imageUrl =
            (imageUrl + "https://storage.googleapis.com/cooke_storage/android/cooke.png") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://storage.googleapis.com/cooke_storage/android/community.png") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://storage.googleapis.com/cooke_storage/android/foodrecog.png") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://storage.googleapis.com/cooke_storage/android/nutritionist.png") as ArrayList<String>

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




















