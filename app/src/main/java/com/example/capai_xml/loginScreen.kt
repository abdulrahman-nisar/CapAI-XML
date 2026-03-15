package com.example.capai_xml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToSignUp = findViewById<Button>(R.id.btnGoToSignUp)

        btnLogin.setOnClickListener {
            // Navigate to HomeScreen upon successful login
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }

        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }
    }
}
