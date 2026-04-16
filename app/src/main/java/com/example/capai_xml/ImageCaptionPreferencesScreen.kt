package com.example.capai_xml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImageCaptionPreferencesScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_caption_preferences_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val generateButton = findViewById<Button>(R.id.btnGenerate)
        val toolbarImageCaptionPreferences = findViewById<Toolbar>(R.id.toolbarCaptionPreferences)
        val selectedImageView = findViewById<ImageView>(R.id.ivSelectedImage)
        val selectedImageUri = intent.getStringExtra("selectedImageUri")?.toUri()
        selectedImageUri?.let {
            selectedImageView.setImageURI(it)
        }

        generateButton.setOnClickListener {
            val intent = Intent(this, ImageDetailsScreen::class.java)
            intent.putExtra("selectedImageUri", selectedImageUri.toString())
            startActivity(intent)
        }

        toolbarImageCaptionPreferences.setNavigationOnClickListener {
            Intent(this, HomeScreen::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)
            }
        }
    }
}