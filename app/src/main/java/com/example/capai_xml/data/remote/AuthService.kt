package com.example.capai_xml.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class AuthService {
    val auth = Firebase.auth

    fun signUpWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Unable to sign up"))
                }
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Unable to sign in"))
                }
            }
    }

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Unable to sign in with Google"))
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

}