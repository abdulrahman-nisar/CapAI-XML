package com.example.capai_xml.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capai_xml.CapAiApp
import com.example.capai_xml.R
import com.example.capai_xml.ui.CapAiViewModel
import com.example.capai_xml.ui.CapAiViewModelFactory

class Splash : AppCompatActivity() {

    private val viewModel: CapAiViewModel by viewModels {
        CapAiViewModelFactory((application as CapAiApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            decideNextScreen()
        }, 2000)
    }

    private fun decideNextScreen() {
        val user = viewModel.currentUser.value

        val destination = when {
            user == null -> MainActivity::class.java
            user.isNewUser -> MainActivity::class.java
            else -> HomeScreen::class.java
        }

        startActivity(Intent(this, destination))
        overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}