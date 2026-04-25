package com.example.capai_xml.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capai_xml.CapAiApp
import com.example.capai_xml.R
import com.example.capai_xml.ui.CapAiViewModel
import com.example.capai_xml.ui.CapAiViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlin.getValue

class SignUpScreen : AppCompatActivity() {

    private val viewModel: CapAiViewModel by viewModels {
        CapAiViewModelFactory((application as CapAiApp).repository)
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data == null) {
            Toast.makeText(this, "Google Sign-In cancelled", Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }

        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    "Google Sign-In failed: missing idToken (check default_web_client_id)",
                    Toast.LENGTH_LONG
                ).show()
                return@registerForActivityResult
            }

            viewModel.signInWithGoogle(
                idToken,
                onSuccess = {
                    val name = account.displayName ?: "Google User"
                    val email = account.email ?: ""
                    if (email.isNotBlank()) {
                        viewModel.addUser(name, email)
                    }
                    Toast.makeText(this, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                },
                onFailure = { exception ->
                    Toast.makeText(this, "Google Sign-In failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            )
        } catch (e: ApiException) {
            Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Google Sign-In error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)

        val signUpButton = findViewById<Button>(R.id.btnSignUp)
        val signUpWithGoogleButton = findViewById<Button>(R.id.btnGoogleSignUp)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signUpButton.setOnClickListener {
            val username = etUsername.text?.toString().orEmpty().trim()
            val email = etEmail.text?.toString().orEmpty().trim()
            val password = etPassword.text?.toString().orEmpty()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.signUpWithEmailAndPassword(
                email = email,
                password = password,
                onSuccess = {
                    auth.currentUser?.updateProfile(
                        userProfileChangeRequest {
                            displayName = username
                        }
                    )
                    viewModel.addUser(username, email)
                    Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                },
                onFailure = { e ->
                    Toast.makeText(this, "Sign up failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        signUpWithGoogleButton.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }
    }


    private fun navigateToHome() {
        startActivity(Intent(this, HomeScreen::class.java))
        finish()
    }
}
