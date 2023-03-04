package com.example.parkingspotfinder.screens.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun logIn(email: String, password: String, onSuccess: () -> Unit)
    = viewModelScope.launch {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    onSuccess()
                }

            }
    }

    fun registerNewUser(email: String, password: String, fullName: String, onSuccess: () -> Unit)
    = viewModelScope.launch {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val userInfo = mutableMapOf<String, String>()
                    userInfo["userId"] = auth.currentUser?.uid.toString()
                    userInfo["fullName"] = fullName
                    firestore.collection("users").add(userInfo)
                    onSuccess()

                }
            }
    }
}