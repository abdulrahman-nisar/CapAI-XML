package com.example.capai_xml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val landingScreenButton = findViewById<Button>(R.id.landing_screen)
        val signupScreenButton = findViewById<Button>(R.id.signup_screen)
        val loginScreenButton = findViewById<Button>(R.id.login_screen)
        val homeScreenButton = findViewById<Button>(R.id.home_screen)
        val selectImageScreenButton = findViewById<Button>(R.id.select_image_screen)
        val selectVideoScreenButton = findViewById<Button>(R.id.select_video_screen)
        val imageCaptionPreferencesScreenButton = findViewById<Button>(R.id.image_caption_preferences_screen)
        val imageDetailsScreenButton = findViewById<Button>(R.id.caption_details_screen)
        val captionTranslationBottomSheet = findViewById<Button>(R.id.translation_bottom_sheet)
        val captionGeneratingScreen = findViewById<Button>(R.id.generating_caption_screen)
        val autoCaptionScreen = findViewById<Button>(R.id.auto_caption_screen)
        val editCaptionScreen = findViewById<Button>(R.id.edit_caption_screen)

        landingScreenButton.setOnClickListener {
            val intent = Intent(this, LandingScreen::class.java)
            startActivity(intent)
        }

        signupScreenButton.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }

        loginScreenButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        homeScreenButton.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        selectImageScreenButton.setOnClickListener {
            val intent = Intent(this, SelectImageScreen::class.java)
            startActivity(intent)
        }
        selectVideoScreenButton.setOnClickListener {
            val intent = Intent(this, SelectVideoScreen::class.java)
            startActivity(intent)
        }
        imageCaptionPreferencesScreenButton.setOnClickListener {
            val intent = Intent(this, ImageCaptionPreferencesScreen::class.java)
            startActivity(intent)
        }
        imageDetailsScreenButton.setOnClickListener {
            val intent = Intent(this, ImageDetailsScreen::class.java)
            startActivity(intent)
        }

        captionTranslationBottomSheet.setOnClickListener {
            val intent = Intent(this, BottomSheetCaptionTranslation::class.java)
            startActivity(intent)
        }

        captionGeneratingScreen.setOnClickListener {
            val intent = Intent(this, GeneratingCaptionScreen::class.java)
            startActivity(intent)
        }

        autoCaptionScreen.setOnClickListener {
            val intent = Intent(this, AutoCaptionScreen::class.java)
            startActivity(intent)
        }

        editCaptionScreen.setOnClickListener {
            val intent = Intent(this, EditCaptionScreen::class.java)
            startActivity(intent)
        }
    }
}