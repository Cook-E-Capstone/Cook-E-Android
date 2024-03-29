package com.example.cooke.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cooke.AuthData
import com.example.cooke.UserPreferences
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: UserPreferences) : ViewModel() {

    fun getAuthSettings(): LiveData<AuthData> {

        return pref.getUserSetting().asLiveData()
    }

    fun saveAuthSetting(name : String, email : String, password : String) {
        viewModelScope.launch {
            pref.saveUserSetting(name, email, password)
        }
    }

    fun removeAuthSetting() {
        viewModelScope.launch {
            pref.saveUserSetting("","","")
        }
    }

}