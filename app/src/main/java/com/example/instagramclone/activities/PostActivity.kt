package com.example.instagramclone.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.UserPreferences
import com.example.instagramclone.databinding.ActivityPostBinding
import com.example.instagramclone.models.AuthViewModel
import com.example.instagramclone.network.ApiConfig
import com.example.instagramclone.network.responses.ImageUploadResponse
import com.example.instagramclone.reduceFileImage
import com.example.instagramclone.rotateFile
import com.example.instagramclone.utils.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PostActivity : AppCompatActivity() {

    private var getFile: File? = null


    private lateinit var binding: ActivityPostBinding

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (!allPermissionsGranted()) {
//                Toast.makeText(
//                    this,
//                    "Tidak mendapatkan permission.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }
//
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (!allPermissionsGranted()) {
//            ActivityCompat.requestPermissions(
//                this,
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//            )
//        }

        Log.d("TEST", "${binding.previewImageView.drawable == null}")

//        showScanButton(binding.previewImageView.drawable == null)


        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.actionBack.setOnClickListener {
            finish()
        }

        val pref = UserPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(this, ViewModelFactory(pref, this,"")).get(
            AuthViewModel::class.java
        )

        authViewModel.getAuthSettings().observe(this) { authData ->
            binding.uploadButton.setOnClickListener { uploadImage(authData.token) }
        }
    }

//    private fun showScanButton(isImageExist: Boolean) {
//        binding.uploadButton.visibility = if (isImageExist) View.VISIBLE else View.GONE
//    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun uploadImage(token : String) {
        if (getFile != null ) {
            val file = reduceFileImage(getFile as File)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val apiService = ApiConfig.getApiService()
            val uploadImageRequest = apiService.uploadImage("Bearer $token",imageMultipart)
            uploadImageRequest.enqueue(object : Callback<ImageUploadResponse> {
                override fun onResponse(
                    call: Call<ImageUploadResponse>,
                    response: Response<ImageUploadResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Toast.makeText(this@PostActivity, responseBody.data?.name, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@PostActivity, ResultActivity::class.java)
                            intent.putExtra("data", responseBody.data?.nutrition)
                            startActivity(intent)

                            // TODO : USE THIS CODE INSTEAD WHEN DONE TESTING
//                            if (responseBody.data?.confidence!! < 0.7) {
//                                Toast.makeText(this@PostActivity, "Please retake your photo", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(this@PostActivity, responseBody.data?.name, Toast.LENGTH_SHORT).show()
//                                val intent = Intent(this@PostActivity, ResultActivity::class.java)
//                                intent.putExtra("data", responseBody.data?.nutrition)
//                                startActivity(intent)
//                            }
                        }
                    } else {
                        Log.d("INIBANG", "load: ${response.message()}")
                        Toast.makeText(this@PostActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                    Toast.makeText(this@PostActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        } else {
            val warnMessage = "Please provide an image"
            Toast.makeText(this@PostActivity, warnMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }
}