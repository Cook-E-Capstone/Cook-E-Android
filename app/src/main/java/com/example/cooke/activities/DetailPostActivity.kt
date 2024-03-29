package com.example.cooke.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cooke.UserPreferences
import com.example.cooke.databinding.ActivityDetailPostBinding
import com.example.cooke.utils.formatDate
import com.example.cooke.models.AuthViewModel
import com.example.cooke.models.MainViewModel
import com.example.cooke.network.responses.CommunityDetail
import com.example.cooke.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class DetailPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPostBinding

    companion object {
        const val EXTRA_ID = "extra_story_id"
        const val TAG = "DetailPostActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getStringExtra(EXTRA_ID)
        Log.d(TAG, "onCreate: $postId")

        binding.ivBack.setOnClickListener {
            finishAfterTransition()

        }


        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref,this,""))[AuthViewModel::class.java]

        authViewModel.getAuthSettings().observe(this) { it ->
            mainViewModel.getDetailPost(it.token, postId!!)
                mainViewModel.detailPostData.observe(this) {
                    Log.d(TAG, "onCreate: $it")
                    setData(it)
                }

        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setData(data: CommunityDetail) {
        val caption = "${data.user?.name} • ${formatDate(data.createdAt!!)}"

        Glide.with(binding.ivDetail.context)
            .load(data.pathfile)
            .centerCrop()
            .into(binding.ivDetail)
        binding.tvDetailTitle.text = data.title
        binding.tvCaption.text = caption
        binding.tvContent.text = data.content
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

