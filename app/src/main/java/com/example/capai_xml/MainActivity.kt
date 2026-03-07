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
        val selectImageScreenButton = findViewById<Button>(R.id.select_image_screen)
        val selectVideoScreenButton = findViewById<Button>(R.id.select_video_screen)
        val imageCaptionPreferencesScreenButton = findViewById<Button>(R.id.image_caption_preferences_screen)
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
    }
}