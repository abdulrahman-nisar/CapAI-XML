package com.example.capai_xml.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.capai_xml.R

class SignUpScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)

        val signUpButton = findViewById<Button>(R.id.btnSignUp)
        val signUpWithFacebookButton = findViewById<Button>(R.id.btnFbSignUp)
        val signUpWithGoogleButton = findViewById<Button>(R.id.btnGoogleSignUp)

        signUpButton.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        signUpWithFacebookButton.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)

        }

        signUpWithGoogleButton.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)

        }
    }
}
